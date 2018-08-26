package com.puc.tcc.vendas.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompraDTO {

	private Long id;

	private String codigoDaCompra;

	@NotNull()
	private String codigoDoProduto;
	
	@NotNull()
	private String idFornecedor;

	@NotNull()
	private int quantidade;
	
	private BigDecimal valorDaCompra;

}