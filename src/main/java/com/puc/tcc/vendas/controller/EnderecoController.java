package com.puc.tcc.vendas.controller;

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

import com.puc.tcc.vendas.dtos.EnderecoDTO;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.service.EnderecoService;

@RestController()
@RequestMapping("/endereco")
public class EnderecoController {

	private EnderecoService enderecoService;

	@Autowired
	public EnderecoController(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}
	
	@GetMapping("/cep/{cep}")
	public ResponseEntity<EnderecoDTO> buscarCep(@PathVariable(value = "cep") String cep) throws VendaException {

		EnderecoDTO enderecoDTO = enderecoService.buscarCep(cep);
		return new ResponseEntity<EnderecoDTO>(enderecoDTO, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EnderecoDTO> consultar(@PathVariable(value = "id") Long id) throws VendaException {

		EnderecoDTO enderecoDTO = enderecoService.consultar(id);
			
		return new ResponseEntity<EnderecoDTO>(enderecoDTO, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<EnderecoDTO> incluir(@RequestBody @Valid EnderecoDTO enderecoDTO) {

		EnderecoDTO responseEnderecoDTO = enderecoService.incluir(enderecoDTO);
		return new ResponseEntity<EnderecoDTO>(responseEnderecoDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<EnderecoDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid EnderecoDTO enderecoDTODetails) throws VendaException {
		
		EnderecoDTO enderecoDTO = enderecoService.atualizar(id, enderecoDTODetails);

		return new ResponseEntity<EnderecoDTO>(enderecoDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<EnderecoDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {
		
		ResponseEntity<EnderecoDTO> response = enderecoService.deletar(id);

		return response;
	}

}