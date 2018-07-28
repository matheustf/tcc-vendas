package com.puc.vendas.dtos;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FornecedorDTO {

	private Long id;

	@NotNull()
	private String nomeFantasia;
	
	@NotNull()
	private String razaoSocial;
	
	@CNPJ
	@NotNull()
	private String cnpj;
	
	@NotNull()
	private String ramoDeAtividade;
	
	@NotNull
	private String email;
}