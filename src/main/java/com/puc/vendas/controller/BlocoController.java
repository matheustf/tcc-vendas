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

import com.puc.vendas.dtos.BlocoDTO;
import com.puc.vendas.exceptions.VendaException;
import com.puc.vendas.service.BlocoService;

@RestController
@RequestMapping("/blocos")
public class BlocoController {
	
	private BlocoService blocoService;

	@Autowired
	public BlocoController(BlocoService blocoService) {
		this.blocoService = blocoService;
	}

	@GetMapping()
	@RequestMapping("")
	public ResponseEntity<List<BlocoDTO>> buscarTodos() {

		List<BlocoDTO> listBlocos = blocoService.buscarTodos();

		return new ResponseEntity<List<BlocoDTO>>(listBlocos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BlocoDTO> consultar(@PathVariable(value = "id") Long idBloco) throws VendaException {

		BlocoDTO blocoDTO = blocoService.consultar(idBloco);

		return new ResponseEntity<BlocoDTO>(blocoDTO, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<BlocoDTO> incluir(@RequestBody @Valid BlocoDTO blocoDTO) {

		BlocoDTO responseBlocoDTO = blocoService.incluir(blocoDTO);
		return new ResponseEntity<BlocoDTO>(responseBlocoDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BlocoDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid BlocoDTO blocoDTODetails) throws VendaException {

		BlocoDTO blocoDTO = blocoService.atualizar(id, blocoDTODetails);

		return new ResponseEntity<BlocoDTO>(blocoDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<BlocoDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {

		ResponseEntity<BlocoDTO> response = blocoService.deletar(id);
		
		return response;
	}

}