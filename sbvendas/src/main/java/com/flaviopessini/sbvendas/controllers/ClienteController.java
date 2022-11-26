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

    /**
     * Obtém o cliente pelo id.
     *
     * @param id código do cliente.
     * @return Cliente
     */
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

    /**
     * Registra o cliente.
     *
     * @param cliente Objeto Cliente com os dados.
     * @return Cliente.
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        if (cliente == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Corpo da requisição inválido"
            );
        }
        final var result = this.clienteRepository.save(cliente);
        return ResponseEntity.ok(result);
    }

    /**
     * Atualiza um cliente existente. Deve ser passado pela url o id do cliente e
     * no corpo da requisição um objeto cliente. Os dados que
     * diferirem no banco de dados serão considerados para atualização.
     *
     * @param id      código do cliente.
     * @param cliente objeto com os dados do cliente para ser atuazalido.
     * @return HTTP 200 | HTTP 404
     */
    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable("id") Integer id, @RequestBody Cliente cliente) {
        return this.clienteRepository.findById(id).map(exists -> {
            // Pega o Id do registro cliente existente e define no novo objeto recebido pela
            // requisição, dessa forma o cliente será atualizado com os novos dados, pois já existe Id.
            cliente.setId(exists.getId());
            this.clienteRepository.save(cliente); // atualiza.
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deleta o cliente.
     *
     * @param id código do cliente.
     * @return Void.
     */
    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (this.clienteRepository.existsById(id)) {
            this.clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
