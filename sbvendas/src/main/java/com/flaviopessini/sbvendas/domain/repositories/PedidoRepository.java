package com.flaviopessini.sbvendas.domain.repositories;

import com.flaviopessini.sbvendas.domain.entities.Cliente;
import com.flaviopessini.sbvendas.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    /**
     * Query Method — Obtém a lista de pedidos do cliente através da propriedade mapeada 'cliente'.
     * @param cliente objeto com as informações do cliente desejado.
     * @return lista de pedidos do cliente.
     */
    List<Pedido> findByCliente(Cliente cliente);
}
