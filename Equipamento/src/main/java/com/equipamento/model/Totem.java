package com.equipamento.model;

import com.equipamento.dto.*;

import java.util.ArrayList;
import java.util.List;

public class Totem {

    private int id;
    private String localizacao;
    private String descricao;
    private List<Tranca> trancas;

    public static final List<Totem> totens = new ArrayList<>();

    public Totem(TotemDTO dadosCadastroTotem) {
        this.id = totens.size() + 1;
        this.descricao = dadosCadastroTotem.descricao();
        this.localizacao = dadosCadastroTotem.localizacao();
        this.trancas = new ArrayList<>();
    }

    public void atualizaTotem(TotemDTO dadosAlteracaoTotem) {
        this.descricao = dadosAlteracaoTotem.descricao();
        this.localizacao = dadosAlteracaoTotem.localizacao();
    }

    public int getId() {
        return this.id;
    }

    public List<Tranca> getTrancas() {
        return this.trancas;
    }
}
