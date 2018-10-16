package com.puc.tcc.vendas.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.exceptions.VendaException;

public class Util {
	public static boolean validar(String cep) throws VendaException {
		if (StringUtils.isBlank(cep) || "00000000".equals(cep) || cep.contains("-") || !cep.matches("\\d{8}")) {
			throw new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND);
		}
		return true;
	}
	
	public static String gerarCodigo(int qtdCaracteres) {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return myRandom.substring(0,qtdCaracteres);
	}
	
	public static String gerarCodigo(String inicio, int qtdCaracteres) {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		return inicio + "-" + myRandom.substring(0,qtdCaracteres);
	}
	
	public static String retirarPrefixo(String codigo) {
		String[] partes = codigo.split("-");
		
		return partes[1];
	}
	
	public static String getPagameterToken(String token, String tokenParameter) throws VendaException {
		try {
			System.out.println(token);
			String[] pieces = token.split("\\.");

			String header = new String(DatatypeConverter.parseBase64Binary(pieces[1]), "UTF-8");

			return getParameter(header, tokenParameter);

		} catch (Exception e) {
			throw new VendaException(HttpStatus.UNAUTHORIZED, Constants.UNAUTHORIZED);
		}
	}
	
	private static String getParameter(String header, String parameter) {
		//TODO REFACTOR
		header.indexOf(parameter);

		int initial = header.lastIndexOf(parameter) + parameter.length() + 3;

		header = header.substring(initial);
		int next = header.indexOf("\"");
		
		return header.substring(0,next);
	}
	
	public static String dataNow() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter
		  .ofLocalizedDateTime(FormatStyle.MEDIUM)
		  .withLocale(new Locale("pt", "br"));
		return agora.format(formatador); //08/04/14 10:02
		
	}
	
	public static String dataFuture(int days) {
		LocalDateTime agora = LocalDateTime.now().plusDays(days);
		DateTimeFormatter formatador = DateTimeFormatter
		  .ofLocalizedDateTime(FormatStyle.MEDIUM)
		  .withLocale(new Locale("pt", "br"));
		return agora.format(formatador); //08/04/14 10:02
		
	}
}
