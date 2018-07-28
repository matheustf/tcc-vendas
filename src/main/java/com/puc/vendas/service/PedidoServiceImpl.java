package com.puc.vendas.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.puc.vendas.consts.Constants;
import com.puc.vendas.dtos.PedidoDTO;
import com.puc.vendas.entity.Pedido;
import com.puc.vendas.exceptions.VendaException;
import com.puc.vendas.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService {

	PedidoRepository pedidoRepository;

	@Autowired
	public PedidoServiceImpl(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
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

		Type listType = new TypeToken<List<PedidoDTO>>(){}.getType();
		List<PedidoDTO> pedidosDTO = modelMapper().map(pedidos, listType);

		return pedidosDTO;
	}

	@Override
	public PedidoDTO incluir(PedidoDTO pedidoDTO) {
		Pedido pedido = modelMapper().map(pedidoDTO, Pedido.class);

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
}
