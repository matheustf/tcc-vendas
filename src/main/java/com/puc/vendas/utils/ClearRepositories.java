package com.puc.vendas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puc.vendas.repository.BlocoRepository;
import com.puc.vendas.repository.EnderecoRepository;

@Component
public class ClearRepositories {

	@Autowired
	private BlocoRepository blocoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public void clear() {
		blocoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}

}
