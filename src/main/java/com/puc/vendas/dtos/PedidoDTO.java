package com.puc.vendas.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PedidoDTO {

	private Long id;

	@NotNull()
	private String nomeDoComprador;
	
	@NotNull()
	private String dataDoPedido;
	
	@NotNull()
	private String formaDePagamento;

	private CompraDTO valorTotal;
	
	@NotNull()
	private CompraDTO compras;
	
	
}