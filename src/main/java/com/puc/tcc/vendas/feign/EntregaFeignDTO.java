package com.puc.tcc.vendas.feign;

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
	private String idFornecedor;
	private String idCompra;
	private String estimativaDeEntrega;
	private String statusDaEntrega;
}
