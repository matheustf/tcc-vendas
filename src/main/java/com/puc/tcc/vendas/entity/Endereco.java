package com.puc.tcc.vendas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String cep;

	@Column(nullable = false)
	private String logradouro;

	@Column(nullable = false)
	private String numero;

	@Column(nullable = true)
	private String bairro;

	@Column(nullable = true)
	private String complemento;

	@Column(nullable = false)
	private String cidade;

	@Column(nullable = false)
	private String estado;
	
	public Endereco update(Endereco endereco, Endereco enderecoDetails) {
		endereco.setCep(enderecoDetails.getCep());
		endereco.setLogradouro(enderecoDetails.getLogradouro());
		endereco.setNumero(enderecoDetails.getNumero());
		endereco.setBairro(enderecoDetails.getBairro());
		endereco.setComplemento(enderecoDetails.getComplemento());
		endereco.setCidade(enderecoDetails.getCidade());
		endereco.setEstado(enderecoDetails.getEstado());

		return endereco;
	}

}
