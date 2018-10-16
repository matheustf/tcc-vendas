package com.puc.tcc.vendas.dtos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PedidoDTO {

	private Long id;
	
	private String statusDoPedido;
	
	private String codigoDoPedido;

	@NotNull()
	private String idCliente;
	
	@NotNull()
	private String nomeDoCliente;
	
	private String emailCliente;
	
	private String dataDoPedido;
	
	@NotNull()
	private String formaDePagamento;

	private BigDecimal valorDoPedido;
	
	@NotNull()
	private List<CompraDTO> compras;
	
	private boolean enderecoCadastrado;
	
	private int diasUteisParaEntrega;
	
	private String estimativaDeEntrega;
	
	private EnderecoDTO endereco;
	
	
}