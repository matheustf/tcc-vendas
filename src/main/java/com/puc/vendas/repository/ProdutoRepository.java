package com.puc.vendas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.puc.vendas.entity.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
	public Optional<Produto> findById(Long id);
	
	public Optional<Produto> findByCodigoDoProduto(String codigo);

	@Query(" SELECT * from Produto p where p.codigoDoProduto =: codigo and p.quantidadeNoEstoque < :quantidade")
	Produto existsProdutoNoEstoque(@Param("codigo") String codigo, int quantidade);

	@Query( "SELECT * from Produto p where p.codigoDoProduto in :codigosDosProdutos")
	public List<Produto> bucarProdutosPorCodigo(List<String> codigosDosProdutos);
}
