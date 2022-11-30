package com.flaviopessini.sbvendas.domain.repositories;

import com.flaviopessini.sbvendas.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Query Method
     * @param login
     * @return
     */
    Optional<Usuario> findByLogin(String login);
}
