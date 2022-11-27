package com.flaviopessini.sbvendas.domain.repositories;

import com.flaviopessini.sbvendas.domain.entities.Cliente;
import com.flaviopessini.sbvendas.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    /**
     * Query Method — Obtém a lista de pedidos do cliente através da propriedade mapeada 'cliente'.
     *
     * @param cliente objeto com as informações do cliente desejado.
     * @return lista de pedidos do cliente.
     */
    List<Pedido> findByCliente(Cliente cliente);

    /**
     * Obtém o pedido e carrega a lista de itens do pedido.
     *
     * @param id código do pedido.
     * @return Pedido ou nulo.
     */
    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
