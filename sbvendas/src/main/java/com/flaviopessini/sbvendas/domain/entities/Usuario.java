package com.flaviopessini.sbvendas.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Length(message = "Campo [login] deve conter de 3 a 20 caracteres.", min = 3, max = 20)
    @NotNull(message = "Campo [login] não pode ser nulo.")
    @NotEmpty(message = "Campo [login] é obrigatório.")
    @Column(name = "login", length = 20, nullable = false, unique = true)
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Length(message = "Campo [senha] deve conter no mínimo 8 caracteres.", min = 8)
    @NotNull(message = "Campo [senha] não pode ser nulo.")
    @NotEmpty(message = "Campo [senha] é obrigatório.")
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "admin")
    private boolean admin;
}
