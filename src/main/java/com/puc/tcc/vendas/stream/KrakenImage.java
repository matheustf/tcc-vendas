package com.puc.tcc.vendas.stream;

import lombok.Data;

@Data
public class KrakenImage {

	private String file_name;
	
	private long original_size;
	
	private long kraked_size;
	
	private long saved_bytes;
	
	private String kraked_url;
	
	private long original_width;
	
	private long original_height;
	
	private boolean success;

}
