package com.puc.tcc.vendas.service;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.dtos.ProdutoDTO;
import com.puc.tcc.vendas.entity.Compra;
import com.puc.tcc.vendas.entity.Produto;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.repository.ProdutoRepository;
import com.puc.tcc.vendas.stream.KrarenStorage;
import com.puc.tcc.vendas.utils.Util;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	ProdutoRepository produtoRepository;
	
	KrarenStorage krarenStorage;
	
	@Autowired
	public ProdutoServiceImpl(ProdutoRepository produtoRepository, KrarenStorage krarenStorage) {
		this.produtoRepository = produtoRepository;
		this.krarenStorage = krarenStorage;
	}

	@Override
	public ProdutoDTO consultar(Long id) throws VendaException {
		
		Optional<Produto> optional = produtoRepository.findById(id);
		Produto produto = validarProduto(optional);
		
		ProdutoDTO produtoDTO = modelMapper().map(produto, ProdutoDTO.class);
		
		return produtoDTO;
	}

	@Override
	public List<ProdutoDTO> buscarTodos() {

		List<Produto> produtos = (List<Produto>) produtoRepository.findAll();

		Type listType = new TypeToken<List<ProdutoDTO>>(){}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);

		return produtosDTO;
	}

	@Override
	public ProdutoDTO incluir(ProdutoDTO produtoDTO) throws VendaException {
		Produto produto = modelMapper().map(produtoDTO, Produto.class);
		
		produto.setCodigoDoProduto(Util.gerarCodigo("PRODUTO",5).toUpperCase());
		produto.setDataDeCadastro(Util.dataNow());
		
		String urlImagem = krarenStorage.post(produtoDTO.getUrlImagem());
		
		produto.setUrlImagem(urlImagem);

		produtoRepository.save(produto);
		
		return modelMapper().map(produto, ProdutoDTO.class);
	}


	@Override
	public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTODetails) throws VendaException {
		
		Optional<Produto> optional = produtoRepository.findById(id);
		Produto produto = validarProduto(optional);
		
		Produto produtoDetails = modelMapper().map(produtoDTODetails, Produto.class);

		produto = produto.update(produto, produtoDetails);

		produtoRepository.save(produto);

		ProdutoDTO produtoDTO = modelMapper().map(produto, ProdutoDTO.class);

		return produtoDTO;
	}

	@Override
	public ResponseEntity<ProdutoDTO> deletar(Long id) throws VendaException {
		
		Optional<Produto> optional = produtoRepository.findById(id);
		validarProduto(optional);
		
		produtoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public void veficarDisponibilidadeDeProdutos(List<Compra> compras) throws VendaException {
		for (Compra compra : compras) {
			
			Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(compra.getCodigoDoProduto());
			Produto produto = validarProduto(optional);
			
			if(produto.getQuantidadeNoEstoque() < compra.getQuantidade()) {
				 new VendaException(HttpStatus.NOT_FOUND, Constants.PRODUTO_INDISPONIVEL);
			}

		}
	}
	

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Produto validarProduto(Optional<Produto> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}

	@Override
	public List<Produto> bucarProdutosPorCodigo(List<String> codigosDosProdutos) {
		
		return produtoRepository.findByCodigoDoProdutoIn(codigosDosProdutos);
	}

	@Override
	public Produto buscarProdutoPorCodigo(String codigoDoProduto) throws VendaException {
		
		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);
		
		Produto produto = validarProduto(optional);
		
		return produto;
	}

	@Override
	public void atualizarQuantidadeEstocada(String codigoDoProduto, int quantidadeInserida) throws VendaException {
		Produto produto = buscarProdutoPorCodigo(codigoDoProduto);
		produto.setQuantidadeNoEstoque(produto.getQuantidadeNoEstoque() + quantidadeInserida);
		
		produtoRepository.save(produto);
	}

	@Override
	public void atualizarEstoque(List<Compra> compras) throws VendaException {
		for (Compra compra : compras) {
			Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(compra.getCodigoDoProduto());
			
			Produto produto = validarProduto(optional);
			
			produto.setQuantidadeNoEstoque(produto.getQuantidadeNoEstoque() - compra.getQuantidade());
			
			produtoRepository.save(produto);
		}
		
	}
}
