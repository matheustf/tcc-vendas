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
public class Fornecedor {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String nomeFantasia;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String razaoSocial;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String cnpj;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String ramoDeAtividade;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String email;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String dataDoCadastro;
	
	// TODO private Endereco endereco;
	
	public Fornecedor update(Fornecedor fornecedor, Fornecedor detailsFornecedor) {
		fornecedor.setNomeFantasia(detailsFornecedor.getNomeFantasia());
		fornecedor.setRazaoSocial(detailsFornecedor.getRazaoSocial());
		fornecedor.setCnpj(detailsFornecedor.getCnpj());
		fornecedor.setRamoDeAtividade(detailsFornecedor.getRamoDeAtividade());
		fornecedor.setEmail(detailsFornecedor.getEmail());
		
		return fornecedor;
	}
	
}
