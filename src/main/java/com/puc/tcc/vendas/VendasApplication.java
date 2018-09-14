package com.puc.tcc.vendas;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.puc.tcc.vendas.entity.Categoria;
import com.puc.tcc.vendas.entity.Produto;
import com.puc.tcc.vendas.repository.CategoriaRepository;
import com.puc.tcc.vendas.repository.PedidoRepository;
import com.puc.tcc.vendas.repository.ProdutoRepository;
import com.puc.tcc.vendas.stream.KrarenStorage;
import com.puc.tcc.vendas.utils.ClearRepositories;

@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@EnableAutoConfiguration
@SpringBootApplication(exclude = JmsAutoConfiguration.class)
public class VendasApplication implements CommandLineRunner {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ClearRepositories clearRepositories;
	
	@Autowired
	KrarenStorage krarenStorage;

	@Override
	public void run(String... arg0) throws Exception {
		clearRepositories.clear();

		Categoria categoriaTenis = Categoria.builder().nome("Tenis").taxaDeCobranca(10)
				.dataDeAtualizacao("20/07/2018 09:59:27").build();
		Categoria categoriaCelular = Categoria.builder().nome("Celular").taxaDeCobranca(15)
				.dataDeAtualizacao("20/07/2018 09:59:27").build();
		Categoria categoriaCD = Categoria.builder().nome("CD").taxaDeCobranca(3)
				.dataDeAtualizacao("20/07/2018 09:59:27").build();

		categoriaRepository.saveAll(Arrays.asList(categoriaTenis, categoriaCelular, categoriaCD));
		
		String urlImagemAdidasPreto = krarenStorage.post("https://br.vmstatic.com/tenis-casual-feminino-adidas-preto-branco-azul-60827494-0-150-07.jpg");
		String urlImagemNikeBranco = krarenStorage.post("http://lojavirtual.hoteldaweb.com.br/image/cache/catalog/produtos/demo/nike-shox-03-150x150.jpg");
		
		Produto tenisAdidasPreto = Produto.builder().codigoDoProduto("PRODUTO-2D50E").categoriaDoProduto("Tenis")
				.urlImagem(urlImagemAdidasPreto).dataDeCadastro("29/07/2018 09:59:27").nome("Adidas Energy Cloud 2.0")
				.marca("Adidas").modelo("Preto").valor(new BigDecimal("300.00")).precoUnitario(new BigDecimal("330.00"))
				.diasUteisParaEntrega(5).aprovado(true).disponivelNoEstoque(true).codigoDoFornecedor("FORNECEDOR-DQSDW").build();

		Produto tenisNikeBranco = Produto.builder().codigoDoProduto("PRODUTO-6A850").categoriaDoProduto("Tenis")
				.urlImagem(urlImagemNikeBranco).dataDeCadastro("29/07/2018 09:59:26").nome("Nike Revolution")
				.marca("Nike").modelo("Branco").valor(new BigDecimal("500.00")).precoUnitario(new BigDecimal("550.00"))
				.diasUteisParaEntrega(8).aprovado(true).disponivelNoEstoque(true).codigoDoFornecedor("FORNECEDOR-FEFTR").build();

		produtoRepository.saveAll(Arrays.asList(tenisAdidasPreto, tenisNikeBranco));

		// Compra compra1 =
		// Compra.builder().codigoDoProduto("2D50E4AC-0FEC").quantidade(3).build();
		// Compra compra2 =
		// Compra.builder().codigoDoProduto("6A8509AF-F1B2").quantidade(1).build();

		// Pedido pedido1 = Pedido.builder().nomeDoComprador("Joao
		// Silva").formaDePagamento(FormaDePagamento.BOLETO).dataDoPedido("29/07/2018
		// 10:00:46").valorDoPedido(compra1.getValorDaCompra().add(compra2.getValorDaCompra())).compras(Arrays.asList(compra1,compra2)).build();

		// pedidoRepository.save(pedido1);

	}
/*
	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
*/
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
}
