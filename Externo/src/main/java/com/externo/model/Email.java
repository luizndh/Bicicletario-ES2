package com.externo.model;

import com.externo.DTO.EmailDTO;

import java.util.ArrayList;
import java.util.List;

public class Email {
    private int id;
    private String email;
    private String assunto;
    private String mensagem;

    public static List<Email> emails = new ArrayList<>();

    public Email(EmailDTO dadosEmail) {
        this.id = emails.size() + 1;
        this.email = dadosEmail.email();
        this.assunto = dadosEmail.assunto();
        this.mensagem = dadosEmail.mensagem();
    }

    public void atualizaEmail(EmailDTO dadosEmail) {
        this.email = dadosEmail.email();
        this.assunto = dadosEmail.assunto();
        this.mensagem = dadosEmail.mensagem();
    }

    public int getId() {
        return this.id;
    }

}
