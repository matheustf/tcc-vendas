package com.puc.tcc.vendas.feign;

import java.util.List;

import org.springframework.stereotype.Component;

//import com.puc.tcc.scheduler.enums.StatusDaEntrega;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EntregaFeignFallback implements EntregaFeign {

	@Override
	public String inserirEntrega(List<EntregaFeignDTO> entregas) {
		return "";
	}

}