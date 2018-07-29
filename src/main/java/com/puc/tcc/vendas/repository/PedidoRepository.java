package com.puc.tcc.vendas.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.tcc.vendas.entity.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long>{
	public Optional<Pedido> findById(Long id);

	public Optional<Pedido> findByCodigoDoPedido(String codigoDoPedido);
}
