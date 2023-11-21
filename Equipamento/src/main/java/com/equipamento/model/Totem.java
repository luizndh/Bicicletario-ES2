package com.equipamento.model;

import com.equipamento.dto.*;

import java.util.ArrayList;
import java.util.List;

public class Totem {

    private int id;
    private String localizacao;
    private String descricao;
    private List<Tranca> trancas;

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static List<Totem> totens = new ArrayList<>();

    public Totem(TotemDTO dadosCadastroTotem) {
        this.id = totens.size() + 1;
        this.descricao = dadosCadastroTotem.descricao();
        this.localizacao = dadosCadastroTotem.localizacao();
        this.trancas = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public List<Tranca> recuperaTrancas() {
        return this.trancas;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getDescricao() {
        return descricao;
    }
}
