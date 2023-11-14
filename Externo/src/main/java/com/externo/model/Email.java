package com.externo.model;

import com.externo.dto.EmailDTO;

import java.util.ArrayList;
import java.util.List;

public class Email {
    private int id;
    private String email;
    private String assunto;
    private String mensagem;

    public static final List<Email> emails = new ArrayList<>();

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

    public String getEmail() {
        return this.email;
    }

    public String getAssunto() {
        return this.assunto;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
