package com.puc.tcc.vendas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TesteAPIController {
	
	@GetMapping("")
	public ResponseEntity<String> testeAPI() {
		return new ResponseEntity<String>("API Vendas OK", HttpStatus.OK);
	}


}
