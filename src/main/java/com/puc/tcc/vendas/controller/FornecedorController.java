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

import com.puc.tcc.vendas.dtos.FornecedorDTO;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.service.FornecedorService;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
	
	private FornecedorService fornecedorService;

	@Autowired
	public FornecedorController(FornecedorService fornecedorService) {
		this.fornecedorService = fornecedorService;
	}

	@GetMapping()
	@RequestMapping("")
	public ResponseEntity<List<FornecedorDTO>> buscarTodos() {

		List<FornecedorDTO> listFornecedors = fornecedorService.buscarTodos();

		return new ResponseEntity<List<FornecedorDTO>>(listFornecedors, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FornecedorDTO> consultar(@PathVariable(value = "id") Long idFornecedor) throws VendaException {

		FornecedorDTO fornecedorDTO = fornecedorService.consultar(idFornecedor);

		return new ResponseEntity<FornecedorDTO>(fornecedorDTO, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<FornecedorDTO> incluir(@RequestBody @Valid FornecedorDTO fornecedorDTO) {

		FornecedorDTO responseFornecedorDTO = fornecedorService.incluir(fornecedorDTO);
		return new ResponseEntity<FornecedorDTO>(responseFornecedorDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<FornecedorDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid FornecedorDTO fornecedorDTODetails) throws VendaException {

		FornecedorDTO fornecedorDTO = fornecedorService.atualizar(id, fornecedorDTODetails);

		return new ResponseEntity<FornecedorDTO>(fornecedorDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<FornecedorDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {

		ResponseEntity<FornecedorDTO> response = fornecedorService.deletar(id);
		
		return response;
	}

}