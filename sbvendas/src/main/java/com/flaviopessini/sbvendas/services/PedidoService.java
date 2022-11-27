package com.flaviopessini.sbvendas.services;

import com.flaviopessini.sbvendas.domain.StatusPedido;
import com.flaviopessini.sbvendas.domain.entities.Pedido;
import com.flaviopessini.sbvendas.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido save(PedidoDTO pedido);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
