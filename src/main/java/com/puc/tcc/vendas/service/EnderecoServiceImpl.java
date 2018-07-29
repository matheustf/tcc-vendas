package com.puc.tcc.vendas.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.puc.tcc.vendas.consts.Constants;
import com.puc.tcc.vendas.dtos.EnderecoDTO;
import com.puc.tcc.vendas.entity.Endereco;
import com.puc.tcc.vendas.exceptions.VendaException;
import com.puc.tcc.vendas.repository.EnderecoRepository;
import com.puc.tcc.vendas.utils.Util;

//TODO
@Service
public class EnderecoServiceImpl implements EnderecoService {

	@Autowired
	EnderecoRepository enderecoRepository;

	public EnderecoDTO buscarCep(String cep) throws VendaException {
		Util.validar(cep);
		
		Endereco endereco = new Endereco();
		int count = cep.length();
		StringBuilder cepBuilder = new StringBuilder(cep);
		String uri = "http://api.postmon.com.br/v1/cep/{cep}";
		RestTemplate restTemplate = new RestTemplate();

		while (endereco.getCep() == null) {
			if (count == 0) {
				//TODO 
				//throw new EnderecoNaoEncontradoException("CEP nao encontrado!");
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("cep", cepBuilder.toString());
			try {
				endereco = restTemplate.getForObject(uri, Endereco.class, params);
			} catch (HttpClientErrorException error) {
				if (error.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
					cepBuilder.setCharAt(--count, '0');
				}
			}
		}
		EnderecoDTO enderecoDTO = modelMapper().map(endereco, EnderecoDTO.class);

		return enderecoDTO;
	}

	@Override
	public EnderecoDTO consultar(Long id) throws VendaException {
		
		Optional<Endereco> optional = enderecoRepository.findById(id);
		Endereco endereco = validarEndereco(optional);
		
		EnderecoDTO enderecoDTO = modelMapper().map(endereco, EnderecoDTO.class);
		
		return enderecoDTO;
	}

	@Override
	public EnderecoDTO incluir(EnderecoDTO enderecoDTO) {
		Endereco endereco = modelMapper().map(enderecoDTO, Endereco.class);
		enderecoRepository.save(endereco);
		
		return modelMapper().map(endereco, EnderecoDTO.class);
	}

	@Override
	public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoDTODetails) throws VendaException {
		Util.validar(enderecoDTODetails.getCep());
		
		Optional<Endereco> optional = enderecoRepository.findById(id);
		Endereco endereco = validarEndereco(optional);
		
		Endereco enderecoDetails = modelMapper().map(enderecoDTODetails, Endereco.class);
		
		endereco = endereco.update(endereco, enderecoDetails);
		
		enderecoRepository.save(endereco);
		
		return modelMapper().map(endereco, EnderecoDTO.class);
	}

	@Override
	public ResponseEntity<EnderecoDTO> deletar(Long id) throws VendaException {
		
		Optional<Endereco> optional = enderecoRepository.findById(id);
		validarEndereco(optional);
		
		enderecoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	private Endereco validarEndereco(Optional<Endereco> optional) throws VendaException {
		return Optional.ofNullable(optional).get()
		.orElseThrow(() -> new VendaException(HttpStatus.NOT_FOUND, Constants.ITEM_NOT_FOUND));
	}
	
}
