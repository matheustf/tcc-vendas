package com.puc.tcc.vendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

import com.puc.vendas.entity.Endereco;
import com.puc.vendas.repository.EnderecoRepository;
import com.puc.vendas.utils.ClearRepositories;


@SpringBootApplication(exclude = JmsAutoConfiguration.class)
@EnableAutoConfiguration
public class VendasApplication implements CommandLineRunner{
	
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ClearRepositories clearRepositories;
	
	@Override
	public void run(String... arg0) throws Exception {
		clearRepositories.clear();
		
		
		
		Endereco endereco = Endereco.builder()
				.cep("09454050")
				.logradouro("Alameda Grajaú")
				.numero("554")
				.bairro("Alphaville Industrial")
				.complemento("apto 1203 bloco B")
				.cidade("Barueri")
				.estado("SP").build();

		enderecoRepository.save(endereco);
		
	}

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

}
