package com.flaviopessini.sbvendas.domain.repositories;

import com.flaviopessini.sbvendas.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Query Method — Carrega uma lista de clientes contendo o nome ou parte do nome informado.
     *
     * @param search nome ou parte do nome.
     * @return lista de clientes encontrados.
     */
    List<Cliente> findByNomeContains(String search);

    /**
     * Query Method — Busca um cliente com o cpf informado.
     *
     * @param cpf cpf do cliente a ser buscado.
     * @return Cliente | NULL.
     */
    Optional<Cliente> findOneByCpf(String cpf);

    /**
     * Exemplo de consulta JPQL — Carrega uma lista de clientes contendo o nome ou parte do nome informado.
     *
     * @param nome ou parte do nome.
     * @return lista de clientes encontrados.
     */
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> buscarPorNomeContendoHQL(@Param("nome") String nome);

    /**
     * Exemplo de consulta com query nativa — Carrega uma lista de clientes contendo o nome ou parte do nome informado.
     *
     * @param nome ou parte do nome.
     * @return lista de clientes encontrados.
     */
    @Query(value = "select * from clientes c where c.nome like '%:nome%'", nativeQuery = true)
    List<Cliente> buscarPorNomeContendoQueryNativa(@Param("nome") String nome);

    /**
     * Consulta JPQL — Obtém o cliente mesmo se não houver pedidos registrados para ele.
     *
     * @param id código do cliente.
     * @return cliente.
     */
    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
