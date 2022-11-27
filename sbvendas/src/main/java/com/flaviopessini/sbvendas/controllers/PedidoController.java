package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.domain.entities.ItemPedido;
import com.flaviopessini.sbvendas.domain.entities.Pedido;
import com.flaviopessini.sbvendas.dto.InformacaoItemPedidoDTO;
import com.flaviopessini.sbvendas.dto.InformacoesPedidoDTO;
import com.flaviopessini.sbvendas.dto.PedidoDTO;
import com.flaviopessini.sbvendas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping(value = "/api/pedidos",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("{id}")
    public InformacoesPedidoDTO findById(@PathVariable Integer id) {
        return this.pedidoService.obterPedidoCompleto(id)
                .map(p -> convertToDTO(p))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado")
                );
    }

    /**
     * Registra um novo pedido.
     *
     * @param dto PedidoDTO.
     * @return código do pedido.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        final var pedido = this.pedidoService.save(dto);
        return pedido.getId();
    }

    /**
     * Converte um objeto Pedido para um DTO de informações de pedido.
     */
    private InformacoesPedidoDTO convertToDTO(Pedido pedido) {
        return InformacoesPedidoDTO.builder()
                .id(pedido.getId())
                .data(pedido.getData())
                .nomeCompletoCliente(pedido.getCliente().getNomeCompleto())
                .cpf(pedido.getCliente().getCpf())
                .total(pedido.getTotal())
                .items(convertToList(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> convertToList(List<ItemPedido> itens) {
        if (CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }

        return itens.stream().map(item -> InformacaoItemPedidoDTO.builder()
                .descricao(item.getProduto().getDescricao())
                .quantidade(item.getQuantidade())
                .valorUnit(item.getValorUnit())
                .build()
        ).collect(Collectors.toList());
    }
}
