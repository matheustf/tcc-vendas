package com.puc.tcc.vendas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.puc.tcc.vendas.entity.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
	public Optional<Produto> findById(Long id);
	
	public Optional<Produto> findByCodigoDoProduto(String codigo);
	
	@Query( "SELECT * from Produto p where p.aprovado=1")
	public List<Produto> findProdutosDisponiveis();

	//@Query( "SELECT * from Produto p where p.codigoDoProduto in :codigosDosProdutos")
	//public List<Produto> bucarProdutosPorCodigo(@Param("codigosDosProdutos") List<String> codigosDosProdutos);
	
	List<Produto> findByCodigoDoProdutoIn(List<String> codigosDosProdutos);
}
