package com.fiap.adm.application.web.configuration;

import com.fiap.adm.domain.ports.in.IPontoUseCasePort;
import com.fiap.adm.domain.ports.in.IPontoQueueListenerPort;
import com.fiap.adm.domain.ports.out.IEmailSenderPort;
import com.fiap.adm.domain.ports.out.IPontoRepositoryPort;
import com.fiap.adm.domain.usecase.PontoUseCase;
import com.fiap.adm.infrastructure.messaging.PontoQueueListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationContext {
    @Bean
    public IPontoUseCasePort pontoUseCasePort(IPontoRepositoryPort pontoRepositoryPort, IEmailSenderPort emailSenderPort) {
        return new PontoUseCase(pontoRepositoryPort, emailSenderPort);
    }
    @Bean
    public IPontoQueueListenerPort pontoQueueListenerPort(IPontoUseCasePort pontoUseCasePort) {
        return new PontoQueueListenerAdapter(pontoUseCasePort);
    }
}
