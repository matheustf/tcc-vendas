package com.puc.tcc.vendas.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.puc.tcc.vendas.enums.FormaDePagamento;
import com.puc.tcc.vendas.enums.StatusDoPedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String codigoDoPedido;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String nomeDoComprador;
	
	@NotNull(message = "Campo Obrigatorio!")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", columnDefinition = "enum('CRIADO','AGUARDANDO_PAGAMENTO', 'PAGO','EFETUADO')")
	private StatusDoPedido statusDoPedido;
	
	@NotNull(message = "Campo Obrigatorio!")
	@Enumerated(EnumType.STRING)
	@Column(name = "formaDePagamento", columnDefinition = "enum('BOLETO','DEBITO','CREDITO')")
	private FormaDePagamento formaDePagamento;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private BigDecimal valorDoPedido;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String dataDoPedido;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Compra> compras;
	
	public Pedido update(Pedido pedido, Pedido detailsPedido) {
		pedido.setNomeDoComprador(detailsPedido.getNomeDoComprador());
		pedido.setFormaDePagamento(detailsPedido.getFormaDePagamento());
		pedido.setCompras(detailsPedido.getCompras());
		
		return pedido;
	}
	
}
