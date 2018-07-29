package com.puc.tcc.vendas.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String codigoDoProduto;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private BigDecimal valorDaCompra;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private int quantidade;
	
	public Compra update(Compra compra, Compra detailsCompra) {
		compra.setCodigoDoProduto(detailsCompra.getCodigoDoProduto());
		compra.setQuantidade(detailsCompra.getQuantidade());
		
		return compra;
	}
	

}
