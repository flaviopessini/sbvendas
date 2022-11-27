package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.domain.entities.Produto;
import com.flaviopessini.sbvendas.domain.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping(value = "/api/produtos",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Obtém uma lista de todos os produtos registrados ou de produtos com base em parâmetros de busca
     * definidos no filtro através de uma consulta construída com ExampleMatcher.
     * Exemplo de uso do filtro: http://localhost:8080/api/produtos?titulo=galaxy
     *
     * @param filter propriedade(s) do objeto Produto a serem utilizadas como parâmetro de busca.
     * @return se nenhum filtro for definido retornará uma lista com todos os produtos registrados,
     * ou uma lista de produtos encontrados.
     */
    @GetMapping()
    public List<Produto> find(Produto filter) {
        if (filter == null) {
            return this.produtoRepository.findAll();
        } else {
            final var matcher = ExampleMatcher.matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            final var example = Example.of(filter, matcher);
            return this.produtoRepository.findAll(example);
        }
    }

    /**
     * Obtém o produto pelo id.
     *
     * @param id código do produto.
     * @return Produto.
     */
    @GetMapping("{id}")
    public Produto findById(@PathVariable Integer id) {
        return this.produtoRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
                );
    }

    /**
     * Registra o produto.
     *
     * @param produto Objeto Produto com os dados.
     * @return Produto.
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save(@RequestBody Produto produto) {
        return this.produtoRepository.save(produto);
    }

    /**
     * Atualiza um produto existente. Deve ser passado pela url o id do produto e
     * no corpo da requisição um objeto produto. Os dados que
     * diferirem no banco de dados serão considerados para atualização.
     *
     * @param id      código do produto.
     * @param produto objeto com os dados do produto para ser atuazalido.
     * @return Objeto produto atualizado ou erro 404.
     */
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto update(@PathVariable Integer id, @RequestBody Produto produto) {
        return this.produtoRepository.findById(id).map(exists -> {
            produto.setId(exists.getId());
            this.produtoRepository.save(produto);
            return exists;
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        );
    }

    /**
     * Deleta o produto.
     *
     * @param id código do produto.
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        this.produtoRepository.findById(id).map(produto -> {
            this.produtoRepository.delete(produto);
            return Void.TYPE;
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        );
    }
}
