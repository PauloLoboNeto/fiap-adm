package com.fiap.adm.infrastructure;

import com.fiap.adm.domain.ports.out.IEmailSenderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailSenderAdapter implements IEmailSenderPort {


    private final Environment env;

    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail.host")); // Substitua pelo seu host SMTP
        mailSender.setPort(587); // Substitua pela sua porta SMTP

        mailSender.setUsername(env.getProperty("spring.mail.username")); // Substitua pelo seu id de e-mail
        mailSender.setPassword(env.getProperty("spring.mail.password")); // Substitua pela senha do seu e-mail

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }


    @Override
    public void send(String destinatario, String assunto, String texto) throws MessagingException, UnsupportedEncodingException {
        var mailSender = this.javaMailSender();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("postmaster@sandbox0408845495b64ec5a16012a707ef8e69.mailgun.org", "Hackathon FIAP");
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(texto, true);
        mailSender.send(message);

    }
}
