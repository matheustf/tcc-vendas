package com.puc.tcc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.ProdutoDTO;
import com.puc.tcc.vendas.entity.Compra;
import com.puc.tcc.vendas.entity.Produto;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface ProdutoService {
	
	ProdutoDTO consultar(Long id) throws VendaException;
	
	ProdutoDTO incluir(ProdutoDTO produtoDTO) throws VendaException;
	
	ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTODetails) throws VendaException;
	
	ResponseEntity<ProdutoDTO> deletar(Long id) throws VendaException;

	List<ProdutoDTO> buscarTodos();

	void veficarDisponibilidadeDeProdutos(List<Compra> compras) throws VendaException;

	List<Produto> bucarProdutosPorCodigo(List<String> codigosDosProdutos);

	Produto buscarProdutoPorCodigo(String codigoDoProduto) throws VendaException;

	void atualizarQuantidadeEstocada(String codigoDoProduto, int quantidadeInserida) throws VendaException;

	void atualizarEstoque(List<Compra> compras) throws VendaException;

}
