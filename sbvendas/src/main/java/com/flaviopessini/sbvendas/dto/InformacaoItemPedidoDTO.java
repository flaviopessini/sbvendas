package com.flaviopessini.sbvendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object — Classe que auxilia a definir quais propriedades serão
 * recebidas e transferidas para os services. Assim a consulta retorna apenas
 * os dados necessários para sua finalidade.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoItemPedidoDTO {

    private String descricao;

    private Integer quantidade;

    private BigDecimal valorUnit;
}
