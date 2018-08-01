package com.puc.tcc.vendas.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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
	
	public static void main(String[] args) {
		System.out.println(Util.retirarPrefixo("PEDIDO-DB7BF"));
		
		// String accessToken = OAuth2Client.generateAccessToken();
				/*
				 * RestTemplate restTemplate = new RestTemplate();
				 * 
				 * HttpHeaders headers = new HttpHeaders();
				 * headers.setContentType(MediaType.APPLICATION_JSON);
				 * headers.set("Authorization", "Bearer ");
				 * 
				 * HttpEntity<String> entity = new HttpEntity<String>(request,headers); String
				 * response = restTemplate.postForObject(url, entity, String.class);
				 */

				// HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
				// ResponseEntity<Foo> response = restTemplate
				// .exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);

				RestTemplate restTemplate = new RestTemplate();

				String url = "https://api.kraken.io/v1/url";

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				JSONObject json = new JSONObject();
				JSONObject jsonAuth = new JSONObject();
				jsonAuth.put("api_key", "ede3e7f1d040bff71a56f2a2a768073d");
				jsonAuth.put("api_secret", "87485400970b536f34f386d379336e15c707360b");
				json.put("auth", jsonAuth);

				json.put("url",
						"https://chrisslade.com/wp-content/uploads/2015/03/AC-DC_with_Chris_Slade_The_Grammys_05-150x150.jpg");
				json.put("wait", true);
				
				
				ResponseEntity<String> response = restTemplate.postForEntity(url, json, String.class);

				System.out.println(response.getStatusCodeValue());
		
		
	}
	
	public static String dataNow() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter
		  .ofLocalizedDateTime(FormatStyle.MEDIUM)
		  .withLocale(new Locale("pt", "br"));
		return agora.format(formatador); //08/04/14 10:02
		
	}
}
