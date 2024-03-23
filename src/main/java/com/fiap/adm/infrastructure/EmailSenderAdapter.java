package com.fiap.adm.infrastructure;

import com.fiap.adm.domain.ports.out.IEmailSenderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailSenderAdapter implements IEmailSenderPort {

    private final JavaMailSender mailSender;

    @Override
    public void send(String destinatario, String assunto, String texto) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("postmaster@sandbox1b2c56a608114bd0bcc5cc1ee7298921.mailgun.org", "Hackathon FIAP");
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(texto, true);
        mailSender.send(message);

    }
}
