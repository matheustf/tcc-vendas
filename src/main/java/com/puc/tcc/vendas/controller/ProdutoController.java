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

import com.puc.tcc.vendas.dtos.ProdutoDTO;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	private ProdutoService produtoService;

	@Autowired
	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@GetMapping()
	@RequestMapping("")
	public ResponseEntity<List<ProdutoDTO>> buscarTodos() {

		List<ProdutoDTO> listProdutos = produtoService.buscarTodos();

		return new ResponseEntity<List<ProdutoDTO>>(listProdutos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoDTO> consultar(@PathVariable(value = "id") Long idProduto) throws VendaException {

		ProdutoDTO produtoDTO = produtoService.consultar(idProduto);

		return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<ProdutoDTO> incluir(@RequestBody @Valid ProdutoDTO produtoDTO) throws VendaException {

		ProdutoDTO responseProdutoDTO = produtoService.incluir(produtoDTO);
		return new ResponseEntity<ProdutoDTO>(responseProdutoDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable(value = "id") Long id, @RequestBody @Valid ProdutoDTO produtoDTODetails) throws VendaException {

		ProdutoDTO produtoDTO = produtoService.atualizar(id, produtoDTODetails);

		return new ResponseEntity<ProdutoDTO>(produtoDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ProdutoDTO> deletar(@PathVariable(value = "id") Long id) throws VendaException {

		ResponseEntity<ProdutoDTO> response = produtoService.deletar(id);
		
		return response;
	}

}