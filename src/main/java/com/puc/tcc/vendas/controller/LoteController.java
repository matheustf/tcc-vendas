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

import com.puc.tcc.vendas.dtos.LoteDTO;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.service.LoteService;

@RestController
@RequestMapping("/lotes")
public class LoteController {
	
	private LoteService loteService;

	@Autowired
	public LoteController(LoteService loteService) {
		this.loteService = loteService;
	}

	@GetMapping()
	@RequestMapping("")
	public ResponseEntity<List<LoteDTO>> buscarTodos() {

		List<LoteDTO> listLotes = loteService.buscarTodos();

		return new ResponseEntity<List<LoteDTO>>(listLotes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LoteDTO> consultar(@PathVariable(value = "id") Long idLote) throws VendaException {

		LoteDTO loteDTO = loteService.consultar(idLote);

		return new ResponseEntity<LoteDTO>(loteDTO, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<LoteDTO> incluir(@RequestBody @Valid LoteDTO loteDTO) throws VendaException {

		LoteDTO responseLoteDTO = loteService.incluir(loteDTO);
		return new ResponseEntity<LoteDTO>(responseLoteDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LoteDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid LoteDTO loteDTODetails) throws VendaException {

		LoteDTO loteDTO = loteService.atualizar(id, loteDTODetails);

		return new ResponseEntity<LoteDTO>(loteDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<LoteDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {

		ResponseEntity<LoteDTO> response = loteService.deletar(id);
		
		return response;
	}

}