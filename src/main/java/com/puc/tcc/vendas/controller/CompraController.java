package com.puc.tcc.vendas.controller;

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

import com.puc.tcc.vendas.dtos.CompraDTO;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.service.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {
	
	private CompraService compraService;

	@Autowired
	public CompraController(CompraService compraService) {
		this.compraService = compraService;
	}

	@GetMapping()
	@RequestMapping("")
	public ResponseEntity<List<CompraDTO>> buscarTodos() {

		List<CompraDTO> listCompras = compraService.buscarTodos();

		return new ResponseEntity<List<CompraDTO>>(listCompras, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompraDTO> consultar(@PathVariable(value = "id") Long idCompra) throws VendaException {

		CompraDTO compraDTO = compraService.consultar(idCompra);

		return new ResponseEntity<CompraDTO>(compraDTO, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<CompraDTO> incluir(@RequestBody @Valid CompraDTO compraDTO) {

		CompraDTO responseCompraDTO = compraService.incluir(compraDTO);
		return new ResponseEntity<CompraDTO>(responseCompraDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompraDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid CompraDTO compraDTODetails) throws VendaException {

		CompraDTO compraDTO = compraService.atualizar(id, compraDTODetails);

		return new ResponseEntity<CompraDTO>(compraDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CompraDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {

		ResponseEntity<CompraDTO> response = compraService.deletar(id);
		
		return response;
	}

}