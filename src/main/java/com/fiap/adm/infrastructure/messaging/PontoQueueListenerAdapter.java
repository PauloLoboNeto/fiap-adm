package com.fiap.adm.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.adm.domain.model.Ponto;
import com.fiap.adm.domain.ports.in.IPontoUseCasePort;
import com.fiap.adm.domain.ports.in.IPontoQueueListenerPort;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class PontoQueueListenerAdapter implements IPontoQueueListenerPort {
    private final IPontoUseCasePort pontoUseCasePort;
    @Override
    @SqsListener(queueNames = "${queue.atualiza.registro.ponto}", factory = "defaultSqsListenerContainerFactory")
    public void listen(String message) throws JsonProcessingException, IOException {
        System.out.println(message);
        JsonFactory factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        ObjectMapper mapper = new ObjectMapper(factory);
        Ponto ponto = mapper.reader().forType(Ponto.class).readValue(message);
        pontoUseCasePort.salvar(ponto);
    }
}
