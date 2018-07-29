package com.puc.tcc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.FornecedorDTO;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface FornecedorService {
	
	FornecedorDTO consultar(Long id) throws VendaException;
	
	FornecedorDTO incluir(FornecedorDTO fornecedorDTO);
	
	FornecedorDTO atualizar(Long id, FornecedorDTO fornecedorDTODetails) throws VendaException;
	
	ResponseEntity<FornecedorDTO> deletar(Long id) throws VendaException;

	List<FornecedorDTO> buscarTodos();

}
