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
public class ProdutoDTO {

	private Long id;

	private String codigoDoProduto;

	@NotNull()
	private String nome;
	
	@NotNull()
	private BigDecimal precoUnitario;
	
	@NotNull()
	private String marca;
	
	@NotNull()
	private String modelo;
	
	private String urlImagem;
	
	private String dataDeCadastro;
	
	private int quantidadeNoEstoque;
}