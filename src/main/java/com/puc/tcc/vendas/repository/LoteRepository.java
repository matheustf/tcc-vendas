package com.puc.tcc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.tcc.vendas.entity.Lote;

@Repository
public interface LoteRepository extends CrudRepository<Lote, Long>{
	public Optional<Lote> findById(Long id);
	
}
