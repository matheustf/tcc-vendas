package com.puc.tcc.vendas.dtos;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoteDTO {

	private Long id;

	@NotNull()
	private String nome;
	
	private String codigoDoLote;
	
	private String dataDoLote;
	
	@NotNull()
	private String codigoDoProduto;

	@NotNull()
	private int quantidade;
	
}