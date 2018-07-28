package com.puc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long>{
	public Optional<Pedido> findById(Long id);
}
