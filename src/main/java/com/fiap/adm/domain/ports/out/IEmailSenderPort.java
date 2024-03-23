package com.fiap.adm.domain.ports.out;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface IEmailSenderPort {
    void send(String destinatario, String assunto, String texto) throws MessagingException, UnsupportedEncodingException;
}
