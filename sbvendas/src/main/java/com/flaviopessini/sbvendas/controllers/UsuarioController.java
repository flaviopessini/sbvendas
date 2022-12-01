package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.domain.entities.Usuario;
import com.flaviopessini.sbvendas.domain.repositories.UsuarioRepository;
import com.flaviopessini.sbvendas.dto.CredenciaisDTO;
import com.flaviopessini.sbvendas.dto.TokenDTO;
import com.flaviopessini.sbvendas.exceptions.InvalidPasswordException;
import com.flaviopessini.sbvendas.services.impl.UsuarioServiceImpl;
import com.flaviopessini.sbvendas.services.security.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController()
@RequestMapping(value = "/api/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
        MediaType.APPLICATION_JSON_VALUE)
@Api("API Usuários")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/auth")
    public TokenDTO auth(@RequestBody CredenciaisDTO dto) {
        try {
            final var user = Usuario.builder()
                    .login(dto.getLogin())
                    .senha(dto.getSenha())
                    .build();
            final var userDetails = this.usuarioService.auth(user);
            final var token = this.jwtService.generateToken(user);
            return new TokenDTO(user.getLogin(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException | MalformedJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@RequestBody @Valid Usuario usuario) {
        return this.usuarioService.save(usuario);
    }
}
