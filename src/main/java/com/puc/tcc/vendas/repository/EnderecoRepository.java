package com.puc.tcc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.tcc.vendas.entity.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long>{
	
	public Optional<Endereco> findById(Long id);
	
}
