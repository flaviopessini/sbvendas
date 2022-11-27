package com.flaviopessini.sbvendas.services.impl;

import com.flaviopessini.sbvendas.domain.StatusPedido;
import com.flaviopessini.sbvendas.domain.entities.ItemPedido;
import com.flaviopessini.sbvendas.domain.entities.Pedido;
import com.flaviopessini.sbvendas.domain.repositories.ClienteRepository;
import com.flaviopessini.sbvendas.domain.repositories.ItemPedidoRepository;
import com.flaviopessini.sbvendas.domain.repositories.PedidoRepository;
import com.flaviopessini.sbvendas.domain.repositories.ProdutoRepository;
import com.flaviopessini.sbvendas.dto.ItemPedidoDTO;
import com.flaviopessini.sbvendas.dto.PedidoDTO;
import com.flaviopessini.sbvendas.exceptions.PedidoException;
import com.flaviopessini.sbvendas.exceptions.RegraNegocioException;
import com.flaviopessini.sbvendas.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    /**
     * Registra o pedido e a lista de itens. Com a annotation @Transaction todas
     * as operações no banco de dados devem ser bem sucedidas ou será feito
     * rollback.
     *
     * @param dto Data Transfer Object do Pedido.
     * @return Pedido.
     */
    @Override
    @Transactional
    public Pedido save(PedidoDTO dto) {
        final var cliente = this.clienteRepository.findById(dto.getCliente())
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        final var pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setData(LocalDateTime.now());
        pedido.setTotal(new BigDecimal(0)); // inicializa com valor 0
        pedido.setStatus(StatusPedido.REALIZADO);

        final var itens = convertItems(pedido, dto.getItems());
        pedido.setItens(itens);

        for (var i : itens) {
            var soma = i.getValorUnit().multiply(new BigDecimal(i.getQuantidade()));
            pedido.setTotal(pedido.getTotal().add(soma));
        }

        this.pedidoRepository.save(pedido);
        this.itemPedidoRepository.saveAll(itens);
        pedido.setItens(itens);
        return pedido;
    }

    /**
     * Carrega o pedido e os itens.
     *
     * @param id código do pedido.
     * @return Retorna o pedido ou nulo.
     */
    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return this.pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        this.pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(statusPedido);
            return this.pedidoRepository.save(pedido);
        }).orElseThrow(() ->
                new PedidoException("Pedido não encontrado.")
        );
    }

    /**
     * Converte a lista de ItemPedidoDTO numa lista de itens para o pedido.
     */
    private List<ItemPedido> convertItems(Pedido pedido, List<ItemPedidoDTO> dto) {
        if (dto.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
        }
        return dto.stream().map(i -> {
            final var produto = this.produtoRepository
                    .findById(i.getProduto())
                    .orElseThrow(() ->
                            new RegraNegocioException("Código do produto inválido: " + i.getProduto())
                    );
            final var itemPedido = new ItemPedido();
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(i.getQuantidade());
            itemPedido.setValorUnit(produto.getValorUnit());
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
