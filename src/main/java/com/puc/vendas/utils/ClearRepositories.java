package com.puc.vendas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puc.vendas.repository.CompraRepository;
import com.puc.vendas.repository.EnderecoRepository;
import com.puc.vendas.repository.FornecedorRepository;
import com.puc.vendas.repository.LoteRepository;
import com.puc.vendas.repository.PedidoRepository;
import com.puc.vendas.repository.ProdutoRepository;

@Component
public class ClearRepositories {

	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public void clear() {
		compraRepository.deleteAll();
		fornecedorRepository.deleteAll();
		loteRepository.deleteAll();
		pedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		enderecoRepository.deleteAll();
	}

}
