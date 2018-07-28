package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Lote;

@Repository
public interface LoteRepository extends CrudRepository<Lote, Long>{
	public Optional<Lote> findById(Long id);
}
