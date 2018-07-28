package com.puc.vendas.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.puc.vendas.consts.Constants;
import com.puc.vendas.dtos.ProdutoDTO;
import com.puc.vendas.entity.Produto;
import com.puc.vendas.exceptions.VendaException;
import com.puc.vendas.repository.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
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
	public ProdutoDTO incluir(ProdutoDTO produtoDTO) {
		Produto produto = modelMapper().map(produtoDTO, Produto.class);

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

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Produto validarProduto(Optional<Produto> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}
}
