package com.equipamento.model;

import com.equipamento.DTO.TrancaDTO;

import java.util.ArrayList;
import java.util.List;

public class Tranca {
    private int id;
    private int bicicleta;
    private int numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private StatusTranca status;
    public static List<Tranca> trancas = new ArrayList<>();

    public Tranca(TrancaDTO dadosTranca) {
        this.id = trancas.size() + 1;
        this.bicicleta = 0;
        this.numero = dadosTranca.numero();
        this.localizacao = dadosTranca.localizacao();
        this.anoDeFabricacao = dadosTranca.anoDeFabricacao();
        this.modelo = dadosTranca.modelo();
        this.status = StatusTranca.valueOf(dadosTranca.status());
    }

    public void atualizaTranca(TrancaDTO dadosAlteracaoTranca) {
        this.anoDeFabricacao = dadosAlteracaoTranca.anoDeFabricacao();
        this.modelo = dadosAlteracaoTranca.modelo();
        this.status = StatusTranca.valueOf(dadosAlteracaoTranca.status());
        this.localizacao = dadosAlteracaoTranca.localizacao();
        this.numero = dadosAlteracaoTranca.numero();
    }

    public int getId() {
        return this.id;
    }



}
