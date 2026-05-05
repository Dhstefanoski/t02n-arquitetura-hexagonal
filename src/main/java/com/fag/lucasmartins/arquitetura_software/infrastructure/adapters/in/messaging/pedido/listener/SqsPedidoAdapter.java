package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.listener;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.exceptions.ConsumerSQSException;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoEventDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper.PedidoEventDTOMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SqsPedidoAdapter {

    private static final Logger log = LoggerFactory.getLogger(SqsPedidoAdapter.class);

    private final PedidoServicePort pedidoServicePort;

    public SqsPedidoAdapter(PedidoServicePort pedidoServicePort) {
        this.pedidoServicePort = pedidoServicePort;
    }

    @SqsListener(value = "${aws.sqs.queue.pedido}")
    public void receberMensagem(PedidoEventDTO evento) {
        try {
            log.info("Evento de pedido recebido | origem={} occurredAt={} customerId={} zipCode={} itens={}",
                    evento.getOrigin(),
                    evento.getOccurredAt(),
                    evento.getCustomerId(),
                    evento.getZipCode(),
                    evento.getOrderItems() != null ? evento.getOrderItems().size() : 0);

            final PedidoBO pedidoBO = PedidoEventDTOMapper.toBo(evento);
            final PedidoBO criado = pedidoServicePort.criarPedido(pedidoBO);

            log.info("Pedido persistido via SQS com sucesso | id={} customerId={}",
                    criado.getId(), evento.getCustomerId());
        } catch (Exception e) {
            log.error("Erro ao processar evento de pedido para customerId={}", evento.getCustomerId(), e);
            throw new ConsumerSQSException(
                    "erro ao processar evento de pedido para customerId " + evento.getCustomerId(), e);
        }
    }
}
