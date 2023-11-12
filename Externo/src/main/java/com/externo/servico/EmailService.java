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

    /*
    public Tranca recuperaTrancaPorId(int idTranca) {
        if(!trancas.isEmpty()) {
            for (Tranca t : trancas) {
                if (t.getId() == idTranca) {
                    return t;
                }
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }

    public List<Tranca> recuperaTrancas() {
        return trancas;
    }

    //TODO: validar os dados
    public Tranca cadastraTranca(TrancaDTO dadosCadastroTranca) {
        Tranca t = new Tranca(dadosCadastroTranca);
        trancas.add(t);
        return t;
    }

    //TODO: validar os dados
    public Tranca alteraTranca(int idTranca, TrancaDTO dadosAlteracaoTranca) {
        if(!trancas.isEmpty()) {
            for (Tranca t : trancas) {
                if (t.getId() == idTranca) {
                    t.atualizaTranca(dadosAlteracaoTranca);
                    return t;
                }
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }

    //TODO: validar os dados
    public void excluiTranca(int idTranca) {
        if(!trancas.isEmpty()) {
            for (Tranca t : trancas) {
                if (t.getId() == idTranca) {
                    trancas.remove(t);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }
    */

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


