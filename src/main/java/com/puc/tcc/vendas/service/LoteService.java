package com.puc.tcc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.LoteDTO;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface LoteService {
	
	LoteDTO consultar(Long id) throws VendaException;
	
	LoteDTO incluir(LoteDTO loteDTO) throws VendaException;
	
	LoteDTO atualizar(Long id, LoteDTO loteDTODetails) throws VendaException;
	
	ResponseEntity<LoteDTO> deletar(Long id) throws VendaException;

	List<LoteDTO> buscarTodos();

}
