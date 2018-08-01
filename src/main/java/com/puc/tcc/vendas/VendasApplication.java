package com.puc.tcc.vendas;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

import com.puc.tcc.vendas.entity.Lote;
import com.puc.tcc.vendas.entity.Produto;
import com.puc.tcc.vendas.repository.LoteRepository;
import com.puc.tcc.vendas.repository.PedidoRepository;
import com.puc.tcc.vendas.repository.ProdutoRepository;
import com.puc.tcc.vendas.utils.ClearRepositories;


@SpringBootApplication(exclude = JmsAutoConfiguration.class)
@EnableAutoConfiguration
public class VendasApplication implements CommandLineRunner{
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ClearRepositories clearRepositories;
	
	@Override
	public void run(String... arg0) throws Exception {
		clearRepositories.clear();
		
		String urlImagemAdidasPreto = "https://dl.kraken.io/api/e8/bc/b1/2585b48810adb2764c4a0ec4d3/tenis-adidas-superstar-preto-e-branco-185567-700x600-150x150.jpg";
		String urlImagemNikeBranco = "https://dl.kraken.io/api/d7/c2/5f/21c8c6dcd44880f50cd601abe7/nike-shox-03-150x150.jpg";
		
		Produto tenisAdidasPreto = Produto.builder().codigoDoProduto("PRODUTO-2D50E").urlImagem(urlImagemAdidasPreto).dataDeCadastro("29/07/2018 09:59:27").nome("Adidas Energy Cloud 2.0").marca("Adidas").modelo("Preto").precoUnitario(new BigDecimal("300.00")).quantidadeNoEstoque(10).build();
		Produto tenisNikeBranco = Produto.builder().codigoDoProduto("PRODUTO-6A850").urlImagem(urlImagemNikeBranco).dataDeCadastro("29/07/2018 09:59:26").nome("Nike Revolution").marca("Nike").modelo("Branco").precoUnitario(new BigDecimal("500.00")).quantidadeNoEstoque(10).build();
		
		produtoRepository.saveAll(Arrays.asList(tenisAdidasPreto,tenisNikeBranco));
		
		
		Lote loteAdidasPreto = Lote.builder().nome("Lote Adidas Preto").codigoDoLote("LOTE-CF4F4").dataDoLote("29/07/2018 10:13:14").codigoDoProduto("2D50E4AC-0FEC").quantidade(10).build();
		Lote loteNikeBranco = Lote.builder().nome("Lote Nike Branco").codigoDoLote("LOTE-A8E47").dataDoLote("29/07/2018 10:15:57").codigoDoProduto("6A8509AF-F1B2").quantidade(10).build();
		
		loteRepository.saveAll(Arrays.asList(loteAdidasPreto,loteNikeBranco));
		
		
		//Compra compra1 = Compra.builder().codigoDoProduto("2D50E4AC-0FEC").quantidade(3).build();
		//Compra compra2 = Compra.builder().codigoDoProduto("6A8509AF-F1B2").quantidade(1).build();
		
		//Pedido pedido1 = Pedido.builder().nomeDoComprador("Joao Silva").formaDePagamento(FormaDePagamento.BOLETO).dataDoPedido("29/07/2018 10:00:46").valorDoPedido(compra1.getValorDaCompra().add(compra2.getValorDaCompra())).compras(Arrays.asList(compra1,compra2)).build();
		
		
		//pedidoRepository.save(pedido1);
		
	}

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
