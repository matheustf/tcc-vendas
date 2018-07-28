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
public class CompraDTO {

	private Long id;

	@NotNull()
	private String nome;
	
	@NotNull()
	private String codigoDoProduto;

	@NotNull()
	private int quantidade;

}