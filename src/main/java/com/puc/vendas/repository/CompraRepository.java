package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Compra;

@Repository
public interface CompraRepository extends CrudRepository<Compra, Long>{
	public Optional<Compra> findById(Long id);
}
