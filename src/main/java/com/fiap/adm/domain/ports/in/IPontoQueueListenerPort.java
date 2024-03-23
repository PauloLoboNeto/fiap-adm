package com.fiap.adm.domain.ports.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiap.adm.domain.model.Ponto;

import java.io.IOException;

public interface IPontoQueueListenerPort {
    void listen(String message) throws JsonProcessingException, IOException;
}
