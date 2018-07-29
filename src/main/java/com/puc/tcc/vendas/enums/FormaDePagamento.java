package com.puc.tcc.vendas.enums;

public enum FormaDePagamento {

	BOLETO("BOLETO"), 
	DEBITO("DEBITO"), 
	CREDITO("CREDITO");

	private String descricao;

	private FormaDePagamento() {
	}
	
	private FormaDePagamento(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return this.descricao;
	}
}
