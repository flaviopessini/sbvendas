package com.flaviopessini.sbvendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object — Classe que auxilia a definir quais propriedades serão
 * recebidas e transferidas para os services. Assim a consulta retorna apenas
 * os dados necessários para sua finalidade.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {

    private Integer cliente;

    private BigDecimal total;

    private List<ItemPedidoDTO> items;

}
