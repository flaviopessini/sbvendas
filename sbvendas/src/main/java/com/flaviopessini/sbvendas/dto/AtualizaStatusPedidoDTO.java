package com.flaviopessini.sbvendas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object — Classe que auxilia a definir quais propriedades serão
 * recebidas e transferidas para os services. Assim a consulta retorna apenas
 * os dados necessários para sua finalidade.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizaStatusPedidoDTO {

    @NotNull(message = "Campo [status] não pode ser nulo.")
    private String novoStatus;
}
