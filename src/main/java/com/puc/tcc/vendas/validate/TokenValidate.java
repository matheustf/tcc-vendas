package com.puc.tcc.vendas.validate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.utils.Util;

@Component
public class TokenValidate {
	
	public void tokenSimpleValidate(String token) throws VendaException {
		
		if(StringUtils.isBlank(token)) {
			new VendaException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}

	}
	
	public void tokenValidate(String token) throws VendaException {
		String idCadastro = Util.getPagameterToken(token, "idCadastro");
		
		if(StringUtils.isBlank(idCadastro)) {
			new VendaException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}

	}

	public void tokenValidateFornecedor(String token, String idFornecedor) throws VendaException {
		String idCadastro = Util.getPagameterToken(token, "idCadastro");
		
		if (!isTokenValido(idCadastro, idFornecedor)) {
			new VendaException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}
	}

	private boolean isTokenValido(String idCadastro, String idFornecedor) {
		return StringUtils.isNotBlank(idCadastro) && idCadastro.equals(idFornecedor);
	}

}
