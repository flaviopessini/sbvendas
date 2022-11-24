package com.flaviopessini.sbvendas.domain.repositories;

import com.flaviopessini.sbvendas.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    /**
     * Query Method - Carrega uma lista de produtos contendo o titulo ou parte do titulo informado.
     * @param search titulo ou parte do titulo.
     * @return Lista de produtos encontrados.
     */
    List<Produto> findByTituloContains(String search);
}
