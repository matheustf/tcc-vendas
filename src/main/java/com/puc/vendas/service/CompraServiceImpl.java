package com.puc.vendas.service;

import java.lang.reflect.Type;
import java.math.BigDecimal;
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
import com.puc.vendas.dtos.CompraDTO;
import com.puc.vendas.entity.Compra;
import com.puc.vendas.entity.Produto;
import com.puc.vendas.exceptions.VendaException;
import com.puc.vendas.repository.CompraRepository;

@Service
public class CompraServiceImpl implements CompraService {

	CompraRepository compraRepository;
	
	ProdutoService produtoService;

	@Autowired
	public CompraServiceImpl(CompraRepository compraRepository) {
		this.compraRepository = compraRepository;
	}

	@Override
	public CompraDTO consultar(Long id) throws VendaException {
		
		Optional<Compra> optional = compraRepository.findById(id);
		Compra compra = validarCompra(optional);
		
		CompraDTO compraDTO = modelMapper().map(compra, CompraDTO.class);
		
		return compraDTO;
	}

	@Override
	public List<CompraDTO> buscarTodos() {

		List<Compra> compras = (List<Compra>) compraRepository.findAll();

		Type listType = new TypeToken<List<CompraDTO>>(){}.getType();
		List<CompraDTO> comprasDTO = modelMapper().map(compras, listType);

		return comprasDTO;
	}

	@Override
	public CompraDTO incluir(CompraDTO compraDTO) {
		Compra compra = modelMapper().map(compraDTO, Compra.class);

		compraRepository.save(compra);
		
		return modelMapper().map(compra, CompraDTO.class);
	}

	@Override
	public CompraDTO atualizar(Long id, CompraDTO compraDTODetails) throws VendaException {
		
		Optional<Compra> optional = compraRepository.findById(id);
		Compra compra = validarCompra(optional);
		
		Compra compraDetails = modelMapper().map(compraDTODetails, Compra.class);

		compra = compra.update(compra, compraDetails);

		compraRepository.save(compra);

		CompraDTO compraDTO = modelMapper().map(compra, CompraDTO.class);

		return compraDTO;
	}

	@Override
	public ResponseEntity<CompraDTO> deletar(Long id) throws VendaException {
		
		Optional<Compra> optional = compraRepository.findById(id);
		validarCompra(optional);
		
		compraRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public BigDecimal calcularValorDaCompra(List<Compra> compras) {
		BigDecimal valorTotalPedido = null;
		for (Compra compra : compras) {
			Produto produto = produtoService.buscarProdutoPorCodigo(compra.getCodigoDoProduto());;
			BigDecimal precoTotalQtd = produto.getPrecoUnitario().multiply(new BigDecimal(compra.getQuantidade()));
			compra.setValorDaCompra(precoTotalQtd);
			
			valorTotalPedido.add(precoTotalQtd);
		}
		
		return valorTotalPedido;
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Compra validarCompra(Optional<Compra> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}
}
