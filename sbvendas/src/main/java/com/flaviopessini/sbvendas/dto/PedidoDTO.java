package com.flaviopessini.sbvendas.dto;

import com.flaviopessini.sbvendas.validations.annotations.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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

    @NotNull(message = "Campo [cliente] não pode ser nulo.")
    private Integer cliente;

    @PositiveOrZero(message = "Campo [total] deve ser positivo ou 0.")
    @NotNull(message = "Campo [total] não pode ser nulo.")
    private BigDecimal total;

    @NotEmptyList(message = "Lista [itens] não pode ser nula ou vazia.")
    private List<ItemPedidoDTO> items;

}
