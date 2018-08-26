package com.puc.tcc.vendas.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.dtos.PedidoDTO;
import com.puc.tcc.vendas.entity.Compra;
import com.puc.tcc.vendas.entity.Pedido;
import com.puc.tcc.vendas.enums.StatusDoPedido;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.feign.EntregaFeign;
import com.puc.tcc.vendas.feign.EntregaFeignDTO;
import com.puc.tcc.vendas.rabbitmq.RabbitMQComponent;
import com.puc.tcc.vendas.repository.PedidoRepository;
import com.puc.tcc.vendas.utils.Util;

@Service
public class PedidoServiceImpl implements PedidoService {

	PedidoRepository pedidoRepository;

	CompraService compraService;

	RabbitMQComponent rabbitMQComponent;

	EntregaFeign entregaFeign;

	@Autowired
	public PedidoServiceImpl(PedidoRepository pedidoRepository, CompraService compraService, EntregaFeign entregaFeign,
			RabbitMQComponent rabbitMQComponent) {
		this.pedidoRepository = pedidoRepository;
		this.compraService = compraService;
		this.rabbitMQComponent = rabbitMQComponent;
		this.entregaFeign = entregaFeign;
	}

	@Override
	public PedidoDTO consultar(Long id) throws VendaException {

		Optional<Pedido> optional = pedidoRepository.findById(id);
		Pedido pedido = validarPedido(optional);

		PedidoDTO pedidoDTO = modelMapper().map(pedido, PedidoDTO.class);

		return pedidoDTO;
	}

	@Override
	public List<PedidoDTO> buscarTodos() {

		List<Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();

		Type listType = new TypeToken<List<PedidoDTO>>() {
		}.getType();
		List<PedidoDTO> pedidosDTO = modelMapper().map(pedidos, listType);

		return pedidosDTO;
	}

	@Override
	public PedidoDTO incluir(PedidoDTO pedidoDTO) throws VendaException {
		Pedido pedido = modelMapper().map(pedidoDTO, Pedido.class);

		BigDecimal valorDoPedido = compraService.calcularValorDaCompra(pedido.getCompras());

		pedido.setValorDoPedido(valorDoPedido);
		pedido.setCodigoDoPedido(Util.gerarCodigo("PEDIDO", 5).toUpperCase());
		pedido.setDataDoPedido(Util.dataNow());
		pedido.setStatusDoPedido(StatusDoPedido.AGUARDANDO_PAGAMENTO);
		pedido.setDiasUteisParaEntrega(compraService.diasUteisParaEntrega(pedido.getCompras()));

		for (Compra compra : pedido.getCompras()) {
			compra.setCodigoDaCompra(Util.gerarCodigo("COMPRA", 5));
		}

		pedidoRepository.save(pedido);

		return modelMapper().map(pedido, PedidoDTO.class);
	}

	@Override
	public PedidoDTO atualizar(Long id, PedidoDTO pedidoDTODetails) throws VendaException {

		Optional<Pedido> optional = pedidoRepository.findById(id);
		Pedido pedido = validarPedido(optional);

		Pedido pedidoDetails = modelMapper().map(pedidoDTODetails, Pedido.class);

		pedido = pedido.update(pedido, pedidoDetails);

		pedidoRepository.save(pedido);

		PedidoDTO pedidoDTO = modelMapper().map(pedido, PedidoDTO.class);

		return pedidoDTO;
	}

	@Override
	public ResponseEntity<PedidoDTO> deletar(Long id) throws VendaException {

		Optional<Pedido> optional = pedidoRepository.findById(id);
		validarPedido(optional);

		pedidoRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	private Pedido validarPedido(Optional<Pedido> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
				.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}

	@Override
	public PedidoDTO pagarPedido(String codigoDoPedido) throws VendaException {

		Optional<Pedido> optional = pedidoRepository.findByCodigoDoPedido(codigoDoPedido);

		Pedido pedido = validarPedido(optional);

		validarStatusAguardandoPagamento(pedido.getStatusDoPedido());

		pedido.setStatusDoPedido(StatusDoPedido.PAGO);
		
		//TODO Computar feriados e dias nao uteis
		
		pedido.setEstimativaDeEntrega(Util.dataFuture(pedido.getDiasUteisParaEntrega()));
		
		pedidoRepository.save(pedido);
		
		efetuarPedido(codigoDoPedido);

		PedidoDTO pedidoDTO = modelMapper().map(pedido, PedidoDTO.class);

		return pedidoDTO;
	}

	@Override
	public PedidoDTO efetuarPedido(String codigoDoPedido) throws VendaException {

		Optional<Pedido> optional = pedidoRepository.findByCodigoDoPedido(codigoDoPedido);

		Pedido pedido = validarPedido(optional);

		validarStatusPago(pedido.getStatusDoPedido());

		pedido.setStatusDoPedido(StatusDoPedido.EFETUADO);
		
		
		
		List<EntregaFeignDTO> entregas = new ArrayList<>();
		
		for (Compra compra : pedido.getCompras()) {
			
		EntregaFeignDTO entrega = EntregaFeignDTO.builder()
				.idCliente(pedido.getIdCliente())
				.idFornecedor(compra.getIdFornecedor())
				.idCompra(compra.getCodigoDaCompra())
				.estimativaDeEntrega(pedido.getEstimativaDeEntrega())
				
				.statusDaEntrega("EM_SEPARACAO")
				
				.build();
		
		entregas.add(entrega);
		
		}
		
		//TODO INSERT MAIS DE UMA ENTREGA AO MESMO TEMPO
		entregaFeign.inserirEntrega(entregas);

		pedidoRepository.save(pedido);
		rabbitMQComponent.sendPedido(pedido);
		
		PedidoDTO pedidoDTO = modelMapper().map(pedido, PedidoDTO.class);

		return pedidoDTO;
	}

	private void validarStatusAguardandoPagamento(StatusDoPedido statusDoPedido) {
		if (statusDoPedido != StatusDoPedido.AGUARDANDO_PAGAMENTO) {
			new VendaException(HttpStatus.NOT_FOUND, Constants.INCONSISTENCIA_NO_PEDIDO);
		}
	}

	private void validarStatusPago(StatusDoPedido statusDoPedido) {
		if (statusDoPedido != StatusDoPedido.PAGO) {
			new VendaException(HttpStatus.NOT_FOUND, Constants.PAGAGAMENTO_NAO_EFETUADO);
		}
	}

	private List<Pedido> validarPedidoList(Optional<List<Pedido>> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
				.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}

	@Override
	public List<PedidoDTO> buscarPedidosPorCliente(String idCliente) throws VendaException {

		Optional<List<Pedido>> optional = pedidoRepository.findAllByIdCliente(idCliente);

		List<Pedido> pedidos = validarPedidoList(optional);

		Type listType = new TypeToken<List<PedidoDTO>>() {
		}.getType();
		List<PedidoDTO> pedidosDTO = modelMapper().map(pedidos, listType);

		return pedidosDTO;
	}

}
