package com.puc.vendas.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.puc.vendas.enums.FormaDePagamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String nomeDoComprador;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private FormaDePagamento formaDePagamento;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private BigDecimal valorDoPedido;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private List<Compra> compras;
	
	public Pedido update(Pedido pedido, Pedido detailsPedido) {
		pedido.setNomeDoComprador(detailsPedido.getNomeDoComprador());
		pedido.setFormaDePagamento(detailsPedido.getFormaDePagamento());
		pedido.setCompras(detailsPedido.getCompras());
		
		return pedido;
	}
	
}
