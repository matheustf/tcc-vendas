package com.puc.tcc.vendas;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

import com.puc.vendas.entity.Bloco;
import com.puc.vendas.entity.Endereco;
import com.puc.vendas.repository.BlocoRepository;
import com.puc.vendas.repository.EnderecoRepository;
import com.puc.vendas.utils.ClearRepositories;


@SpringBootApplication(exclude = JmsAutoConfiguration.class)
@EnableAutoConfiguration
public class VendasApplication implements CommandLineRunner{
	
	@Autowired
	private BlocoRepository blocoRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClearRepositories clearRepositories;
	
	@Override
	public void run(String... arg0) throws Exception {
		clearRepositories.clear();
		
		
		Bloco blocoA = Bloco.builder().nome("A").build();
		Bloco blocoB = Bloco.builder().nome("B").build();
		
		List<Bloco> blocos = Arrays.asList(blocoA, blocoB);
		
		Endereco endereco = Endereco.builder()
				.cep("09454050")
				.logradouro("Alameda Grajaú")
				.numero("554")
				.bairro("Alphaville Industrial")
				.complemento("apto 1203 bloco B")
				.cidade("Barueri")
				.estado("SP").build();

		enderecoRepository.save(endereco);
		blocoRepository.saveAll(blocos);
		
	}

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
