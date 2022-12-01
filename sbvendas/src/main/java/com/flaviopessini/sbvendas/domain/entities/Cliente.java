package com.flaviopessini.sbvendas.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Length(message = "Campo [nome] deve conter de 3 a 20 caracteres.", min = 3, max = 20)
    @NotNull(message = "Campo [nome] não pode ser nulo.")
    @NotEmpty(message = "Campo [nome] é obrigatório.")
    @Column(name = "nome", length = 20, nullable = false)
    private String nome;

    @Length(message = "Campo [sobrenome] deve conter de 3 a 50 caracteres.", min = 3, max = 50)
    @NotNull(message = "Campo [sobrenome] não pode ser nulo.")
    @NotEmpty(message = "Campo [sobrenome] é obrigatório.")
    @Column(name = "sobrenome", length = 50, nullable = false)
    private String sobrenome;

    @Length(message = "Campo [cpf] deve conter 11 caracteres.", min = 11, max = 15)
    @NotEmpty(message = "Campo [cpf] é obrigatório.")
    @NotNull(message = "Campo [cpf] não pode ser nulo.")
    @CPF(message = "Informe um CPF válido.")
    @Column(name = "cpf", length = 15, nullable = false, unique = true)
    private String cpf;

    @Column(name = "entity_version")
    @Version
    private Integer entityVersion;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Pedido> pedidos;

    /**
     * @return Nome completo do cliente.
     */
    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        Cliente cliente = (Cliente) o;
        return id != null && Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
