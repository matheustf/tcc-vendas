package com.puc.tcc.vendas.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.puc.tcc.vendas.repository.CategoriaRepository;
import com.puc.tcc.vendas.repository.CompraRepository;
import com.puc.tcc.vendas.repository.PedidoRepository;
import com.puc.tcc.vendas.repository.ProdutoRepository;

@Component
public class ClearRepositories {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public void clear() {
		compraRepository.deleteAll();
		pedidoRepository.deleteAll();
		produtoRepository.deleteAll();
		categoriaRepository.deleteAll();
	}

}
