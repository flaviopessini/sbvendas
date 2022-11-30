package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.domain.entities.Usuario;
import com.flaviopessini.sbvendas.domain.repositories.UsuarioRepository;
import com.flaviopessini.sbvendas.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping(value = "/api/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
        MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@RequestBody @Valid Usuario usuario){
        return this.usuarioService.save(usuario);
    }
}
