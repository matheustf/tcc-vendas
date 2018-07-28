package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long>{
	public Optional<Produto> findById(Long id);
}
