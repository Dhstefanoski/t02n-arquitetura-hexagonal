package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.OrderItemEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoEventDTO;

import java.util.ArrayList;
import java.util.List;

public class PedidoEventDTOMapper {

    private PedidoEventDTOMapper() {
    }

    public static PedidoBO toBo(PedidoEventDTO evento) {
        final PedidoBO bo = new PedidoBO();

        final PessoaBO pessoa = new PessoaBO();
        pessoa.setId(evento.getCustomerId());
        bo.setPessoa(pessoa);

        bo.setCep(evento.getZipCode());

        final List<PedidoProdutoBO> itens = new ArrayList<>();
        if (evento.getOrderItems() != null) {
            for (OrderItemEventDTO item : evento.getOrderItems()) {
                itens.add(toItemBo(item));
            }
        }
        bo.setItens(itens);

        return bo;
    }

    private static PedidoProdutoBO toItemBo(OrderItemEventDTO item) {
        final PedidoProdutoBO itemBO = new PedidoProdutoBO();

        final ProdutoBO produto = new ProdutoBO();
        produto.setId(item.getSku());
        itemBO.setProduto(produto);

        itemBO.setQuantidade(item.getAmount() != null ? item.getAmount() : 0);
        return itemBO;
    }
}
