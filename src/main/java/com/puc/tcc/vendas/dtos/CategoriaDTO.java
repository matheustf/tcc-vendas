package com.puc.tcc.vendas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoriaDTO {

	private Long id;

	private String nome;
	
	private int taxaDeCobranca;
	
	private String dataDeAtualizacao;
}