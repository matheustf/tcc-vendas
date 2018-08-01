package com.puc.tcc.vendas.stream;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.puc.tcc.vendas.VendasApplication;
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

		JSONObject json = new JSONObject();
		JSONObject jsonAuth = new JSONObject();
		jsonAuth.put("api_key", "ede3e7f1d040bff71a56f2a2a768073d");
		jsonAuth.put("api_secret", "87485400970b536f34f386d379336e15c707360b");
		json.put("auth", jsonAuth);

		json.put("url",
				"https://chrisslade.com/wp-content/uploads/2015/03/AC-DC_with_Chris_Slade_The_Grammys_05-150x150.jpg");
		json.put("wait", true);
		
		ResponseEntity<KrakenImage> response = restTemplate.postForEntity(url, json, KrakenImage.class);
		
		System.out.println("URL IMAGEM KRAKEN: " + response.getBody().getKraked_url());
		
		return response.getBody().getKraked_url();
	}

}
