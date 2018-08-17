package com.puc.tcc.vendas.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Iterator;
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
import com.puc.tcc.vendas.dtos.CategoriaDTO;
import com.puc.tcc.vendas.dtos.ProdutoDTO;
import com.puc.tcc.vendas.entity.Produto;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.repository.ProdutoRepository;
import com.puc.tcc.vendas.stream.KrarenStorage;
import com.puc.tcc.vendas.utils.Util;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	ProdutoRepository produtoRepository;
	
	KrarenStorage krarenStorage;
	
	CategoriaService categoriaService;
	
	@Autowired
	public ProdutoServiceImpl(ProdutoRepository produtoRepository, KrarenStorage krarenStorage, CategoriaService categoriaService) {
		this.produtoRepository = produtoRepository;
		this.krarenStorage = krarenStorage;
		this.categoriaService = categoriaService;
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
		
		for (Iterator<Produto> it = produtos.iterator(); it.hasNext(); ) {  
			Produto produto = it.next();  
			if (!produto.isDisponivelNoEstoque())
			{
				it.remove();
			}   
		}
	
		Type listType = new TypeToken<List<ProdutoDTO>>(){}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);
		

		return produtosDTO;
	}
	
	@Override
	public List<ProdutoDTO> buscarProdutosIndisponiveis() {

		List<Produto> produtos = (List<Produto>) produtoRepository.findAll();
		
		for (Iterator<Produto> it = produtos.iterator(); it.hasNext(); ) {  
			Produto produto = it.next();  
			if (produto.isDisponivelNoEstoque())
			{
				it.remove();
			}   
		}
	
		Type listType = new TypeToken<List<ProdutoDTO>>(){}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);
		

		return produtosDTO;
	}

	@Override
	public ProdutoDTO incluir(ProdutoDTO produtoDTO) throws VendaException {
		Produto produto = modelMapper().map(produtoDTO, Produto.class);

		CategoriaDTO categoria = categoriaService.buscarCategoriaPorNome(produtoDTO.getCategoriaDoProduto());
		
		produto.setCodigoDoProduto(Util.gerarCodigo("PRODUTO",5).toUpperCase());
		produto.setDataDeCadastro(Util.dataNow());
		produto.setPrecoUnitario((produto.getValor().multiply(new BigDecimal(categoria.getTaxaDeCobranca()).add(new BigDecimal("100")))).divide(new BigDecimal("100")));
		
		String urlImagem = krarenStorage.post(produtoDTO.getUrlImagem());
		
		produto.setUrlImagem(urlImagem);

		produtoRepository.save(produto);
		
		ProdutoDTO produtoDTOreturn = modelMapper().map(produto, ProdutoDTO.class);
		produtoDTOreturn.setCategoria(categoria);
		
		return produtoDTOreturn;
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

}
