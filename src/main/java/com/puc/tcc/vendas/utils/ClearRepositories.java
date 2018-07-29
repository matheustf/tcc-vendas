package com.puc.tcc.vendas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puc.tcc.vendas.repository.CompraRepository;
import com.puc.tcc.vendas.repository.EnderecoRepository;
import com.puc.tcc.vendas.repository.FornecedorRepository;
import com.puc.tcc.vendas.repository.LoteRepository;
import com.puc.tcc.vendas.repository.PedidoRepository;
import com.puc.tcc.vendas.repository.ProdutoRepository;

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
