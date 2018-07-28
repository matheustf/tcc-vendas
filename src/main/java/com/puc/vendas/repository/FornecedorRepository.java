package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Fornecedor;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, Long>{
	public Optional<Fornecedor> findById(Long id);
}
