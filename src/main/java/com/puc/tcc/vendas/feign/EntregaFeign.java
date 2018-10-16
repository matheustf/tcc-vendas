package com.puc.tcc.vendas.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.puc.tcc.vendas.feign.dto.EntregaFeignDTO;

@FeignClient(value = "microservice-entrega", fallback = EntregaFeignFallback.class)
public interface EntregaFeign {

	@RequestMapping(value = "/entregas/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	String inserirEntrega(@RequestBody List<EntregaFeignDTO> entregas);
}
