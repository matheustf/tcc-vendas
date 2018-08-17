package com.puc.tcc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.tcc.vendas.entity.Categoria;

@Repository
public interface CategoriaRepository extends CrudRepository<Categoria, Long>{
	public Optional<Categoria> findById(Long id);
	
	public Optional<Categoria> findByNome(String nome);
}
