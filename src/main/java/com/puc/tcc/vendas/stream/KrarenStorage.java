package com.puc.tcc.vendas.stream;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.exceptions.VendaException;

@Component
public class KrarenStorage {
	
	public String post(String urlImagem) throws VendaException{
		
		try {
			urlImagem = new URI(urlImagem).toString();
		} catch (URISyntaxException e) {
			throw new VendaException(HttpStatus.EXPECTATION_FAILED, Constants.URL_IMAGEM_INCORRETA);
		}
		
		RestTemplate restTemplate = new RestTemplate();

		String url = "https://api.kraken.io/v1/url";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		//try {
		JsonObject json = new JsonObject();
		JsonObject jsonAuth = new JsonObject();
		
			jsonAuth.addProperty("api_key", "ede3e7f1d040bff71a56f2a2a768073d");
	
		jsonAuth.addProperty("api_secret", "87485400970b536f34f386d379336e15c707360b");
		json.add("auth", jsonAuth);

		json.addProperty("url",	urlImagem);
		json.addProperty("wait", true);
		
		//String jsons = json.toString();
		
		String jsons = "{\n" + 
				"\n" + 
				" \"auth\": {\n" + 
				"        \"api_key\": \"ede3e7f1d040bff71a56f2a2a768073d\",\n" + 
				"        \"api_secret\": \"87485400970b536f34f386d379336e15c707360b\"\n" + 
				"    },\n" + 
				"    \n" + 
				"\"url\": \"https://cdn.iset.io/assets/42253/produtos/332634/thumb_150-150-juniorbrancoecinza.jpg\",\n" + 
				"\"wait\": true\n" + 
				"}";
		
		
		ResponseEntity<String> response = restTemplate.postForEntity(url, jsons, String.class);
		
		System.out.println(response);
		//System.out.println("URL IMAGEM KRAKEN: " + response.getBody().getKraked_url());
		
		//return response.getBody().getKraked_url();
		
		//} catch (JSONException e) {
			//throw new VendaException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.PROBLEMA_NO_SERVIDOR);
		//}
		
		return "";
		
	}
	
}
