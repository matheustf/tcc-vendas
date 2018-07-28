package com.puc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.vendas.dtos.ProdutoDTO;
import com.puc.vendas.exceptions.VendaException;

public interface ProdutoService {
	
	ProdutoDTO consultar(Long id) throws VendaException;
	
	ProdutoDTO incluir(ProdutoDTO produtoDTO);
	
	ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTODetails) throws VendaException;
	
	ResponseEntity<ProdutoDTO> deletar(Long id) throws VendaException;

	List<ProdutoDTO> buscarTodos();

}
