package com.puc.tcc.vendas.entity;

import java.math.BigDecimal;

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
public class Produto {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String codigoDoProduto;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private boolean aprovado;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String nome;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private BigDecimal precoUnitario;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private BigDecimal valor;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String marca;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String modelo;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String categoriaDoProduto;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String urlImagem;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private int diasUteisParaEntrega;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String dataDeCadastro;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private boolean disponivelNoEstoque;
	
	@Column(nullable = false)
	@NotNull(message = "Campo Obrigatorio!")
	private String codigoDoFornecedor;
	
	public Produto update(Produto produto, Produto detailsProduto) {
		produto.setCodigoDoProduto(detailsProduto.getCodigoDoProduto());
		produto.setNome(detailsProduto.getNome());
		produto.setMarca(detailsProduto.getMarca());
		produto.setModelo(detailsProduto.getModelo());
		produto.setCategoriaDoProduto(detailsProduto.getCategoriaDoProduto());
		produto.setUrlImagem(detailsProduto.getUrlImagem());
		produto.setDiasUteisParaEntrega(detailsProduto.getDiasUteisParaEntrega());
		produto.setValor(detailsProduto.getValor());
		produto.setDisponivelNoEstoque(detailsProduto.isDisponivelNoEstoque());
		
		return produto;
	}
	
}
