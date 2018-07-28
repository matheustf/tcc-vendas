package com.puc.vendas.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.puc.vendas.consts.Constants;
import com.puc.vendas.exceptions.VendaException;

public class Util {
	public static boolean validar(String cep) throws VendaException {
		if (StringUtils.isBlank(cep) || "00000000".equals(cep) || cep.contains("-") || !cep.matches("\\d{8}")) {
			throw new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND);
		}
		return true;
	}
}
