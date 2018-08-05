package com.puc.tcc.vendas.rabbitmq;

import com.puc.tcc.vendas.entity.Pedido;

public interface RabbitMQComponent {

	void sendPedido(Pedido pedido);

}
