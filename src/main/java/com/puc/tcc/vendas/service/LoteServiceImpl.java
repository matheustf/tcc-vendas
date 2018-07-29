package com.puc.tcc.vendas.service;

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
import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.dtos.LoteDTO;
import com.puc.tcc.vendas.entity.Lote;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.repository.LoteRepository;
import com.puc.tcc.vendas.utils.Util;

@Service
public class LoteServiceImpl implements LoteService {

	LoteRepository loteRepository;
	
	ProdutoService produtoService;

	@Autowired
	public LoteServiceImpl(LoteRepository loteRepository, ProdutoService produtoService) {
		this.loteRepository = loteRepository;
		this.produtoService = produtoService;
	}

	@Override
	public LoteDTO consultar(Long id) throws VendaException {
		
		Optional<Lote> optional = loteRepository.findById(id);
		Lote lote = validarLote(optional);
		
		LoteDTO loteDTO = modelMapper().map(lote, LoteDTO.class);
		
		return loteDTO;
	}

	@Override
	public List<LoteDTO> buscarTodos() {

		List<Lote> lotes = (List<Lote>) loteRepository.findAll();

		Type listType = new TypeToken<List<LoteDTO>>(){}.getType();
		List<LoteDTO> lotesDTO = modelMapper().map(lotes, listType);

		return lotesDTO;
	}

	@Override
	public LoteDTO incluir(LoteDTO loteDTO) throws VendaException {
		Lote lote = modelMapper().map(loteDTO, Lote.class);
		
		lote.setCodigoDoLote(Util.gerarCodigo("LOTE",5).toUpperCase());
		lote.setDataDoLote(Util.dataNow());
		
		produtoService.atualizarQuantidadeEstocada(lote.getCodigoDoProduto(),lote.getQuantidade());

		loteRepository.save(lote);
		
		return modelMapper().map(lote, LoteDTO.class);
	}

	@Override
	public LoteDTO atualizar(Long id, LoteDTO loteDTODetails) throws VendaException {
		
		Optional<Lote> optional = loteRepository.findById(id);
		Lote lote = validarLote(optional);
		
		Lote loteDetails = modelMapper().map(loteDTODetails, Lote.class);

		lote = lote.update(lote, loteDetails);

		loteRepository.save(lote);

		LoteDTO loteDTO = modelMapper().map(lote, LoteDTO.class);

		return loteDTO;
	}

	@Override
	public ResponseEntity<LoteDTO> deletar(Long id) throws VendaException {
		
		Optional<Lote> optional = loteRepository.findById(id);
		validarLote(optional);
		
		loteRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Lote validarLote(Optional<Lote> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}
}
