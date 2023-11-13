package com.externo.servico;

import static com.externo.model.Email.emails;

import com.externo.DTO.CartaoDeCreditoDTO;
import com.externo.DTO.EmailDTO;
import com.externo.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    public boolean enviarEmail(EmailDTO dadosEmail) {
        enviaEmail(dadosEmail.email(), dadosEmail.assunto(), dadosEmail.mensagem());
        return true;
    }

    @Autowired
    private JavaMailSender mailSender;

    private void enviaEmail(String email, String assunto, String mensagem) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(assunto);
        message.setText(mensagem);

        mailSender.send(message);
    }
}


