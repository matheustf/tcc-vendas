package com.puc.tcc.vendas.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class RobbitMqConfig {

	public static final String ROUTING_PEDIDO = "my.queue.pedidos";

	@Bean
	Queue queueCachorro() {
		return new Queue(ROUTING_PEDIDO, true);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("my_queue_exchange");
	}
	
	
	@Bean
	Binding bindingExchangeCachorro(Queue queueCachorro, TopicExchange exchange) {
		return BindingBuilder.bind(queueCachorro).to(exchange).with(ROUTING_PEDIDO);
	}
	
	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}


}
