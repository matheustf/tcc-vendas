package com.puc.tcc.vendas.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

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
	
	@URL
	private String urlImagem;
	
	@NotNull()
	private int diasUteisParaEntrega;
	
	private String dataDeCadastro;
	
	private int quantidadeNoEstoque;
}