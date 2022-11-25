package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.domain.entities.Cliente;
import com.flaviopessini.sbvendas.domain.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/clientes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {

    // Injeta em tempo de execução o repositório do cliente.
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtém uma lista de todos os clientes registrados ou de clientes com base em parâmetros de busca
     * definidos no filtro através de uma consulta construída com ExampleMatcher.
     * Exemplo de uso do filtro: http://localhost:8080/api/clientes?nome=Flavio
     *
     * @param filter propriedade(s) do objeto Cliente a serem utilizadas como parâmetro de busca.
     * @return se nenhum filtro for definido retornará uma lista com todos os clientes registrados,
     * ou uma lista de clientes encontrados.
     */
    @GetMapping()
    @ResponseBody
    public List<Cliente> find(Cliente filter) {
        if (filter == null) {
            return this.clienteRepository.findAll();
        } else {
            final var matcher = ExampleMatcher.matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            final var example = Example.of(filter, matcher);
            return this.clienteRepository.findAll(example);
        }
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity<Cliente> findById(@PathVariable Integer id) {
        final var cliente = this.clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseBody
    public Integer save(@RequestBody Cliente cliente) {
        if (cliente == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Corpo da requisição inválido"
            );
        }
        this.clienteRepository.save(cliente);
        return cliente.getId();
    }
}
