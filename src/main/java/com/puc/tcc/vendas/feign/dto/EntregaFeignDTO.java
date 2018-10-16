package com.puc.tcc.vendas.feign.dto;

import com.puc.tcc.vendas.entity.Endereco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EntregaFeignDTO {

	private String idCliente;
	private String emailCliente;
	private String nomeDoCliente;
	private String idFornecedor;
	private String idCompra;
	private String estimativaDeEntrega;
	private String statusDaEntrega;
	private String nomeDoProduto;
	private String modelo;
	private String marca;
	private int quantidade;
	private Endereco endereco;
	private String urlFornecedor;
}
