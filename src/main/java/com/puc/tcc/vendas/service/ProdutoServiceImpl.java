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
import com.puc.tcc.vendas.validate.TokenValidate;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	ProdutoRepository produtoRepository;

	KrarenStorage krarenStorage;

	CategoriaService categoriaService;
	
	TokenValidate tokenValidate;

	@Autowired
	public ProdutoServiceImpl(ProdutoRepository produtoRepository, TokenValidate tokenValidate, KrarenStorage krarenStorage,
			CategoriaService categoriaService) {
		this.produtoRepository = produtoRepository;
		this.tokenValidate = tokenValidate;
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
		
		List<Produto> produtos = (List<Produto>) produtoRepository.findProdutosDisponiveis();

		for (Iterator<Produto> it = produtos.iterator(); it.hasNext();) {
			Produto produto = it.next();
			if (!produto.isDisponivelNoEstoque()) {
				it.remove();
			}
		}

		Type listType = new TypeToken<List<ProdutoDTO>>() {
		}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);

		return produtosDTO;
	}

	@Override
	public List<ProdutoDTO> buscarProdutosIndisponiveisPorFornecedor(String token) throws VendaException {
		
		String codigoDoFornecedor = Util.getPagameterToken(token, "idCadastro");

		List<Produto> produtos = (List<Produto>) produtoRepository.buscarProdutosDoFornecedorIndisponiveis(codigoDoFornecedor);

		Type listType = new TypeToken<List<ProdutoDTO>>() {
		}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);

		return produtosDTO;
	}

	@Override
	public ProdutoDTO incluir(ProdutoDTO produtoDTO, String token) throws VendaException {
		Produto produto = modelMapper().map(produtoDTO, Produto.class);
		
		String codigoDoFornecedor = Util.getPagameterToken(token, "idCadastro");
		
		produto.setCodigoDoFornecedor(codigoDoFornecedor);

		CategoriaDTO categoria = categoriaService.buscarCategoriaPorNome(produtoDTO.getCategoriaDoProduto());

		produto.setCodigoDoProduto(Util.gerarCodigo("PRODUTO", 5).toUpperCase());
		produto.setDataDeCadastro(Util.dataNow());
		produto.setPrecoUnitario(
				(produto.getValor().multiply(new BigDecimal(categoria.getTaxaDeCobranca()).add(new BigDecimal("100"))))
						.divide(new BigDecimal("100")));
		produto.setAprovado(false);

		String urlImagem = krarenStorage.post(produtoDTO.getUrlImagem());

		produto.setUrlImagem(urlImagem);

		produtoRepository.save(produto);

		ProdutoDTO produtoDTOreturn = modelMapper().map(produto, ProdutoDTO.class);
		produtoDTOreturn.setCategoria(categoria);

		return produtoDTOreturn;
	}

	@Override
	public ProdutoDTO atualizar(String codigoDoProduto, ProdutoDTO produtoDTODetails) throws VendaException {

		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);
		Produto produto = validarProduto(optional);

		Produto produtoDetails = modelMapper().map(produtoDTODetails, Produto.class);

		produto = produto.update(produto, produtoDetails);

		produtoRepository.save(produto);

		ProdutoDTO produtoDTO = modelMapper().map(produto, ProdutoDTO.class);

		return produtoDTO;
	}

	@Override
	public ResponseEntity<ProdutoDTO> deletar(String codigoDoProduto, String token) throws VendaException {

		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);
		Produto produto = validarProduto(optional);

		produtoRepository.deleteById(produto.getId());

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

	@Override
	public ProdutoDTO consultarPorCodigoDoProduto(String codigoDoProduto, String token) throws VendaException {
		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);
		Produto produto = validarProduto(optional);
		
		tokenValidate.tokenValidateFornecedor(token, produto.getCodigoDoFornecedor());

		ProdutoDTO produtoDTO = modelMapper().map(produto, ProdutoDTO.class);

		return produtoDTO;
	}
	
	@Override
	public ProdutoDTO consultarProduto(String codigoDoProduto) throws VendaException {
		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);
		Produto produto = validarProduto(optional);
		
		ProdutoDTO produtoDTO = modelMapper().map(produto, ProdutoDTO.class);

		return produtoDTO;
	}

	@Override
	public void disponibilizar(String codigoDoProduto, String token) throws VendaException {
		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);

		Produto produto = validarProduto(optional);
		
		tokenValidate.tokenValidateFornecedor(token, produto.getCodigoDoFornecedor());
		produto.setDisponivelNoEstoque(true);

		produtoRepository.save(produto);
	}

	@Override
	public void indisponibilizar(String codigoDoProduto, String token) throws VendaException {
		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);

		Produto produto = validarProduto(optional);

		tokenValidate.tokenValidateFornecedor(token, produto.getCodigoDoFornecedor());
		produto.setDisponivelNoEstoque(false);

		produtoRepository.save(produto);
	}
	
	@Override
	public void aprovarProduto(String codigoDoProduto, String token) throws VendaException {
		Optional<Produto> optional = produtoRepository.findByCodigoDoProduto(codigoDoProduto);

		Produto produto = validarProduto(optional);
		
		//TODO

		produto.setAprovado(true);

		produtoRepository.save(produto);
	}

	@Override
	public List<ProdutoDTO> buscarProdutosDisponiveisPorFornecedor(String token) throws VendaException {
		
		String codigoDoFornecedor = Util.getPagameterToken(token, "idCadastro");

		List<Produto> produtos = (List<Produto>) produtoRepository.buscarProdutosDoFornecedorDisponiveis(codigoDoFornecedor);

		Type listType = new TypeToken<List<ProdutoDTO>>() {
		}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);

		return produtosDTO;
	}

	@Override
	public List<ProdutoDTO> buscarProdutosPorFornecedor(String token) throws VendaException {
		
		String codigoDoFornecedor = Util.getPagameterToken(token, "idCadastro");

		List<Produto> produtos = (List<Produto>) produtoRepository.buscarProdutosDoFornecedor(codigoDoFornecedor);

		Type listType = new TypeToken<List<ProdutoDTO>>() {
		}.getType();
		List<ProdutoDTO> produtosDTO = modelMapper().map(produtos, listType);

		return produtosDTO;
	}

}
