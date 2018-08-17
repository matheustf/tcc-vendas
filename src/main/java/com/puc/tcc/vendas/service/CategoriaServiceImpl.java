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
import com.puc.tcc.vendas.dtos.CategoriaDTO;
import com.puc.tcc.vendas.entity.Categoria;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.repository.CategoriaRepository;
import com.puc.tcc.vendas.utils.Util;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	CategoriaRepository categoriaRepository;
	
	@Autowired
	public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@Override
	public CategoriaDTO consultar(Long id) throws VendaException {
		
		Optional<Categoria> optional = categoriaRepository.findById(id);
		Categoria categoria = validarCategoria(optional);
		
		CategoriaDTO categoriaDTO = modelMapper().map(categoria, CategoriaDTO.class);
		
		return categoriaDTO;
	}

	@Override
	public List<CategoriaDTO> buscarTodos() {

		List<Categoria> categorias = (List<Categoria>) categoriaRepository.findAll();

		Type listType = new TypeToken<List<CategoriaDTO>>(){}.getType();
		List<CategoriaDTO> categoriasDTO = modelMapper().map(categorias, listType);

		return categoriasDTO;
	}

	@Override
	public CategoriaDTO incluir(CategoriaDTO categoriaDTO) {
		Categoria categoria = modelMapper().map(categoriaDTO, Categoria.class);
		
		categoria.setDataDeAtualizacao(Util.dataNow());

		categoriaRepository.save(categoria);
		
		return modelMapper().map(categoria, CategoriaDTO.class);
	}

	@Override
	public CategoriaDTO atualizar(Long id, CategoriaDTO categoriaDTODetails) throws VendaException {
		
		Optional<Categoria> optional = categoriaRepository.findById(id);
		Categoria categoria = validarCategoria(optional);
		
		Categoria categoriaDetails = modelMapper().map(categoriaDTODetails, Categoria.class);

		categoria = categoria.update(categoria, categoriaDetails);
		
		categoria.setDataDeAtualizacao(Util.dataNow());

		categoriaRepository.save(categoria);

		CategoriaDTO categoriaDTO = modelMapper().map(categoria, CategoriaDTO.class);

		return categoriaDTO;
	}

	@Override
	public ResponseEntity<CategoriaDTO> deletar(Long id) throws VendaException {
		
		Optional<Categoria> optional = categoriaRepository.findById(id);
		validarCategoria(optional);
		
		categoriaRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	private Categoria validarCategoria(Optional<Categoria> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.CATEGORIA_NOT_FOUND));
	}

	@Override
	public CategoriaDTO buscarCategoriaPorNome(String categoria) throws VendaException {
		Optional<Categoria> optional = categoriaRepository.findByNome(categoria);
		
		Categoria categoriaReturn = validarCategoria(optional);
		return modelMapper().map(categoriaReturn, CategoriaDTO.class);
	}
}
