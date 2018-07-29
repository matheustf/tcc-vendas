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
public class EnderecoDTO {

	private Long id;

	@NotNull(message = "Campo Obrigatorio!")
	private String cep;

	@NotNull(message = "Campo Obrigatorio!")
	private String logradouro;

	@NotNull(message = "Campo Obrigatorio!")
	private String numero;

	@NotNull
	private String bairro;

	private String complemento;

	@NotNull(message = "Campo Obrigatorio!")
	private String cidade;

	@NotNull(message = "Campo Obrigatorio!")
	private String estado;

}
