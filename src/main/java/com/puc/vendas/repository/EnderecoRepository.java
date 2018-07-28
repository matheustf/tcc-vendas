package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long>{
	
	public Optional<Endereco> findById(Long id);
	
}
