package com.externo.servico;

import com.externo.dto.EmailDTO;
import com.externo.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public Email enviarEmail(EmailDTO dadosEmail) {
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


