package com.flaviopessini.sbvendas.domain.repositories;

import com.flaviopessini.sbvendas.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Query Method - Carrega uma lista de clientes contendo o nome ou parte do nome informado.
     * @param search nome ou parte do nome.
     * @return lista de clientes encontrados.
     */
    List<Cliente> findByNomeContains(String search);
}
