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
import com.puc.tcc.vendas.dtos.FornecedorDTO;
import com.puc.tcc.vendas.entity.Fornecedor;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.repository.FornecedorRepository;
import com.puc.tcc.vendas.utils.Util;

@Service
public class FornecedorServiceImpl implements FornecedorService {

	FornecedorRepository fornecedorRepository;

	@Autowired
	public FornecedorServiceImpl(FornecedorRepository fornecedorRepository) {
		this.fornecedorRepository = fornecedorRepository;
	}

	@Override
	public FornecedorDTO consultar(Long id) throws VendaException {
		
		Optional<Fornecedor> optional = fornecedorRepository.findById(id);
		Fornecedor fornecedor = validarFornecedor(optional);
		
		FornecedorDTO fornecedorDTO = modelMapper().map(fornecedor, FornecedorDTO.class);
		
		return fornecedorDTO;
	}

	@Override
	public List<FornecedorDTO> buscarTodos() {

		List<Fornecedor> fornecedores = (List<Fornecedor>) fornecedorRepository.findAll();

		Type listType = new TypeToken<List<FornecedorDTO>>(){}.getType();
		List<FornecedorDTO> fornecedoresDTO = modelMapper().map(fornecedores, listType);

		return fornecedoresDTO;
	}

	@Override
	public FornecedorDTO incluir(FornecedorDTO fornecedorDTO) {
		Fornecedor fornecedor = modelMapper().map(fornecedorDTO, Fornecedor.class);

		fornecedor.setDataDoCadastro(Util.dataNow());
		
		fornecedorRepository.save(fornecedor);
		
		return modelMapper().map(fornecedor, FornecedorDTO.class);
	}

	@Override
	public FornecedorDTO atualizar(Long id, FornecedorDTO fornecedorDTODetails) throws VendaException {
		
		Optional<Fornecedor> optional = fornecedorRepository.findById(id);
		Fornecedor fornecedor = validarFornecedor(optional);
		
		Fornecedor fornecedorDetails = modelMapper().map(fornecedorDTODetails, Fornecedor.class);

		fornecedor = fornecedor.update(fornecedor, fornecedorDetails);

		fornecedorRepository.save(fornecedor);

		FornecedorDTO fornecedorDTO = modelMapper().map(fornecedor, FornecedorDTO.class);

		return fornecedorDTO;
	}

	@Override
	public ResponseEntity<FornecedorDTO> deletar(Long id) throws VendaException {
		
		Optional<Fornecedor> optional = fornecedorRepository.findById(id);
		validarFornecedor(optional);
		
		fornecedorRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Fornecedor validarFornecedor(Optional<Fornecedor> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}
}
