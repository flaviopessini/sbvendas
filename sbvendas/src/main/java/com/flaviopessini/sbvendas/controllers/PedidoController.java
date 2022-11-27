package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.dto.PedidoDTO;
import com.flaviopessini.sbvendas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/api/pedidos",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        final var pedido = this.pedidoService.save(dto);
        return pedido.getId();
    }
}
