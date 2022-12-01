package com.flaviopessini.sbvendas.domain.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Length(message = "Campo [titulo] deve conter no máximo 150 caracteres.", min = 2, max = 150)
    @NotEmpty(message = "Campo [titulo] é obrigatório")
    @NotNull(message = "Campo [titulo] não pode ser nulo.")
    @Column(name = "titulo", length = 150)
    private String titulo;

    @Length(message = "Campo [descricao] deve conter no máximo 255 caracteres.", min = 0, max = 255)
    @Column(name = "descricao", length = 255)
    private String descricao;

    @PositiveOrZero(message = "Campo [valorUnit] deve ser positivo ou 0.")
    @NotNull(message = "Campo [valorUnit] não pode ser nulo.")
    @Column(name = "valor_unit", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorUnit;

    @Column(name = "entity_version")
    @Version
    private Integer entityVersion;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Produto produto = (Produto) o;
        return id != null && Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
