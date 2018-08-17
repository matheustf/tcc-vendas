package com.puc.tcc.vendas.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.PedidoDTO;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface PedidoService {
	
	PedidoDTO consultar(Long id) throws VendaException;
	
	PedidoDTO incluir(PedidoDTO pedidoDTO) throws VendaException;
	
	PedidoDTO atualizar(Long id, PedidoDTO pedidoDTODetails) throws VendaException;
	
	ResponseEntity<PedidoDTO> deletar(Long id) throws VendaException;

	List<PedidoDTO> buscarTodos();

	PedidoDTO pagarPedido(String codigoDoPedido) throws VendaException;

	PedidoDTO efetuarPedido(String codigoDoPedido) throws VendaException;

}
