package com.puc.vendas.service;

import org.springframework.http.ResponseEntity;

import com.puc.vendas.dtos.EnderecoDTO;
import com.puc.vendas.exceptions.VendaException;

public interface EnderecoService {
	
	EnderecoDTO consultar(Long id) throws VendaException;
	
	EnderecoDTO incluir(EnderecoDTO enderecoDTO);
	
	ResponseEntity<EnderecoDTO> deletar(Long id) throws VendaException;
	
	EnderecoDTO buscarCep(String cep) throws VendaException;

	EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTODetails) throws VendaException;

}
