package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Bloco;

@Repository
public interface BlocoRepository extends CrudRepository<Bloco, Long>{
	public Optional<Bloco> findById(Long id);
}
