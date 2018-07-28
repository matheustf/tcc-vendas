package com.puc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.vendas.dtos.BlocoDTO;
import com.puc.vendas.exceptions.VendaException;

public interface BlocoService {
	
	BlocoDTO consultar(Long id) throws VendaException;
	
	BlocoDTO incluir(BlocoDTO blocoDTO);
	
	BlocoDTO atualizar(Long id, BlocoDTO blocoDTODetails) throws VendaException;
	
	ResponseEntity<BlocoDTO> deletar(Long id) throws VendaException;

	List<BlocoDTO> buscarTodos();

}
