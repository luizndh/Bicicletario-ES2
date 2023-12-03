package com.externo.servico;

import com.externo.dto.EmailDTO;
import com.externo.model.Email;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.internet.InternetAddress;
import java.util.NoSuchElementException;

@Service
public class EmailService {

    public Email enviarEmail(EmailDTO dadosEmail) {
        if (!EmailValidator.getInstance().isValid(dadosEmail.email())) {
            throw new IllegalArgumentException("Email com formato invalido");
        }

        try {
            InternetAddress emailAddr = new InternetAddress(dadosEmail.email());
            emailAddr.validate();
        } catch (Exception e) {
            throw new NoSuchElementException("Email nao existe");
        }

        enviaEmail(dadosEmail.email(), dadosEmail.assunto(), dadosEmail.mensagem());
        return new Email(dadosEmail);
    }

    @Autowired
    private JavaMailSender mailSender;

    private void enviaEmail(String email, String assunto, String mensagem) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bcicleta0@gmail.com");
        message.setTo(email);
        message.setSubject(assunto);
        message.setText(mensagem);

        mailSender.send(message);
    }
}


