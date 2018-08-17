package com.puc.tcc.vendas.entity;

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
public class Categoria {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String nome;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private int taxaDeCobranca;

	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String dataDeAtualizacao;
	
	public Categoria update(Categoria categoriaDoProduto, Categoria detailsCategoriaDoProduto) {
		categoriaDoProduto.setNome(detailsCategoriaDoProduto.getNome());
		categoriaDoProduto.setTaxaDeCobranca(detailsCategoriaDoProduto.getTaxaDeCobranca());
		
		return categoriaDoProduto;
	}
	

}
