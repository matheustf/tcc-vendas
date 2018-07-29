package com.puc.tcc.vendas.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.puc.tcc.vendas.dtos.CompraDTO;
import com.puc.tcc.vendas.entity.Compra;
import com.puc.tcc.vendas.exceptions.VendaException;

public interface CompraService {
	
	CompraDTO consultar(Long id) throws VendaException;
	
	CompraDTO incluir(CompraDTO compraDTO);
	
	CompraDTO atualizar(Long id, CompraDTO compraDTODetails) throws VendaException;
	
	ResponseEntity<CompraDTO> deletar(Long id) throws VendaException;

	List<CompraDTO> buscarTodos();

	BigDecimal calcularValorDaCompra(List<Compra> compras) throws VendaException;

}
