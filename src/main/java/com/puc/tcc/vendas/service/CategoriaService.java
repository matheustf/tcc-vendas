package com.puc.tcc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.CategoriaDTO;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface CategoriaService {
	
	CategoriaDTO consultar(Long id) throws VendaException;
	
	CategoriaDTO incluir(CategoriaDTO categoriaDTO);
	
	CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTODetails) throws VendaException;
	
	ResponseEntity<CategoriaDTO> deletar(Long id) throws VendaException;

	List<CategoriaDTO> buscarTodos();

	CategoriaDTO buscarCategoriaPorNome(String categoria) throws VendaException;

}
