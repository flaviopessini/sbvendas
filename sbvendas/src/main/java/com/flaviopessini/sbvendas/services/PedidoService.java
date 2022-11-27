package com.flaviopessini.sbvendas.services;

import com.flaviopessini.sbvendas.domain.entities.Pedido;
import com.flaviopessini.sbvendas.dto.PedidoDTO;

public interface PedidoService {

    Pedido save(PedidoDTO pedido);
}
