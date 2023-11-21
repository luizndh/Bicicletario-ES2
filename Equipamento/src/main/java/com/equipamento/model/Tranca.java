package com.equipamento.model;

import com.equipamento.dto.*;

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
    private List<InclusaoTrancaDTO> historicoInclusao;
    private List<RetiradaTrancaDTO> historicoRetirada;

    public static List<Tranca> trancas = new ArrayList<>();



    public enum StatusTranca {
        LIVRE,
        OCUPADA,
        NOVA,
        APOSENTADA,
        EM_REPARO
    }

    public Tranca(TrancaDTO dadosTranca) throws IllegalArgumentException {
        this.id = trancas.size() + 1;
        this.bicicleta = 0;
        this.numero = dadosTranca.numero();
        this.localizacao = dadosTranca.localizacao();
        this.anoDeFabricacao = dadosTranca.anoDeFabricacao();
        this.modelo = dadosTranca.modelo();
        this.status = StatusTranca.valueOf(dadosTranca.status());
        this.historicoInclusao = new ArrayList<>();
        this.historicoRetirada = new ArrayList<>();
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setAnoDeFabricacao(String anoDeFabricacao) {
        this.anoDeFabricacao = anoDeFabricacao;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void atualizaTranca(TrancaDTO dadosAlteracaoTranca) throws IllegalArgumentException {
        this.anoDeFabricacao = dadosAlteracaoTranca.anoDeFabricacao();
        this.modelo = dadosAlteracaoTranca.modelo();
        this.status = StatusTranca.valueOf(dadosAlteracaoTranca.status());
        this.localizacao = dadosAlteracaoTranca.localizacao();
        this.numero = dadosAlteracaoTranca.numero();
    }

    public int getId() {
        return this.id;
    }

    public void setStatus(StatusTranca acao) {
        this.status = acao;
    }

    public void setBicicleta(int idBicicleta) {
        this.bicicleta = idBicicleta;
    }

    public String getModelo() {
        return this.modelo;
    }

    public StatusTranca getStatus() {
        return this.status;
    }

    public int getBicicleta() { return this.bicicleta; }

    public int getNumero() {
        return numero;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getAnoDeFabricacao() {
        return anoDeFabricacao;
    }

    public void adicionaRegistroNoHistoricoDeInclusao(InclusaoTrancaDTO dadosInclusao) {
        this.historicoInclusao.add(dadosInclusao);
    }
    public void adicionaRegistroNoHistoricoDeRetirada(RetiradaTrancaDTO dadosRetirada) {
        this.historicoRetirada.add(dadosRetirada);
    }

}
