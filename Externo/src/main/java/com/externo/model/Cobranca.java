package com.externo.model;

import com.externo.DTO.CobrancaDTO;

import java.util.ArrayList;
import java.util.List;

public class Cobranca {

    private int id;
    private StatusCobranca status;
    private String horaSolicitacao;
    private String horaFinalizacao;
    private float valor;
    private int ciclista;

    public static List<Cobranca> cobrancas = new ArrayList<>();

    public Cobranca(CobrancaDTO dadosCadastroCobranca) {
        this.id = cobrancas.size() + 1;
        this.status = StatusCobranca.valueOf(dadosCadastroCobranca.status());
        this.horaSolicitacao = dadosCadastroCobranca.horaSolicitacao();
        this.horaFinalizacao = dadosCadastroCobranca.horaFinalizacao();
        this.valor = dadosCadastroCobranca.valor();
        this.ciclista = dadosCadastroCobranca.ciclista();
    }

    public void atualizaCobranca(CobrancaDTO dadosCadastroCobranca) {
        this.status = StatusCobranca.valueOf(dadosCadastroCobranca.status());
        this.horaSolicitacao = dadosCadastroCobranca.horaSolicitacao();
        this.horaFinalizacao = dadosCadastroCobranca.horaFinalizacao();
        this.valor = dadosCadastroCobranca.valor();
        this.ciclista = dadosCadastroCobranca.ciclista();
    }

    public enum StatusCobranca {
        PENDENTE,
        PAGA,
        FALHA,
        CANCELADA,
        OCUPADA
    }

    public int getId() {
        return this.id;
    }
}
