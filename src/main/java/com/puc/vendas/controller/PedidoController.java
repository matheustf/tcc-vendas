package com.puc.vendas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puc.vendas.dtos.PedidoDTO;
import com.puc.vendas.exceptions.VendaException;
import com.puc.vendas.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	private PedidoService pedidoService;

	@Autowired
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	@GetMapping()
	@RequestMapping("")
	public ResponseEntity<List<PedidoDTO>> buscarTodos() {

		List<PedidoDTO> listPedidos = pedidoService.buscarTodos();

		return new ResponseEntity<List<PedidoDTO>>(listPedidos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PedidoDTO> consultar(@PathVariable(value = "id") Long idPedido) throws VendaException {

		PedidoDTO pedidoDTO = pedidoService.consultar(idPedido);

		return new ResponseEntity<PedidoDTO>(pedidoDTO, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<PedidoDTO> incluir(@RequestBody @Valid PedidoDTO pedidoDTO) {

		PedidoDTO responsePedidoDTO = pedidoService.incluir(pedidoDTO);
		return new ResponseEntity<PedidoDTO>(responsePedidoDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PedidoDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid PedidoDTO pedidoDTODetails) throws VendaException {

		PedidoDTO pedidoDTO = pedidoService.atualizar(id, pedidoDTODetails);

		return new ResponseEntity<PedidoDTO>(pedidoDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PedidoDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {

		ResponseEntity<PedidoDTO> response = pedidoService.deletar(id);
		
		return response;
	}

}