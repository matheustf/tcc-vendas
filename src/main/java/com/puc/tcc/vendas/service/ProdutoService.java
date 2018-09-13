package com.puc.tcc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.ProdutoDTO;
import com.puc.tcc.vendas.entity.Produto;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface ProdutoService {
	
	ProdutoDTO consultar(Long id) throws VendaException;
	
	ProdutoDTO incluir(ProdutoDTO produtoDTO) throws VendaException;
	
	ProdutoDTO atualizar(String codigoDoProduto, ProdutoDTO produtoDTODetails) throws VendaException;
	
	ResponseEntity<ProdutoDTO> deletar(String codigoDoProduto) throws VendaException;

	List<ProdutoDTO> buscarTodos();

	List<Produto> bucarProdutosPorCodigo(List<String> codigosDosProdutos);

	Produto buscarProdutoPorCodigo(String codigoDoProduto) throws VendaException;

	List<ProdutoDTO> buscarProdutosIndisponiveis();

	ProdutoDTO consultarPorCodigoDoProduto(String codigoDoProduto) throws VendaException;

	void disponibilizar(String codigoDoProduto) throws VendaException;

	void indisponibilizar(String codigoDoProduto) throws VendaException;

	void aprovarProduto(String codigoDoProduto) throws VendaException;

}
