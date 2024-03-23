package com.fiap.adm.domain.ports.in;

import com.fiap.adm.domain.model.Ponto;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IPontoUseCasePort {
    Optional<Ponto> salvar(Ponto ponto);
    List<Ponto> buscarPorPeriodo(Date inicio, Date fim);
    void sendEmail(String destinatario, String assunto, String texto) throws MessagingException, UnsupportedEncodingException;
}
