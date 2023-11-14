package com.aluguel.model;

import java.util.ArrayList;
import java.util.List;

import com.aluguel.DTO.AluguelDTO;

public class Aluguel {

    private int bicicleta;
    private String horaInicio;
    private int trancaFim;
    private String horaFim;
    private int cobranca;
    private int ciclista;
    private int trancaInicio;

    public static List<Aluguel> alugueis = new ArrayList<>();

    public Aluguel(AluguelDTO dadosCadastroAluguel) {
        this.bicicleta = dadosCadastroAluguel.bicicleta();
        this.horaInicio = dadosCadastroAluguel.horaInicio();
        this.trancaFim = dadosCadastroAluguel.trancaFim();
        this.horaFim = dadosCadastroAluguel.horaFim();
        this.cobranca = dadosCadastroAluguel.cobranca();
        this.ciclista = dadosCadastroAluguel.ciclista();
        this.trancaInicio = dadosCadastroAluguel.trancaInicio();
    }

    public void atualizaAluguel(AluguelDTO dadosAlteracaoAluguel) {
        this.bicicleta = dadosAlteracaoAluguel.bicicleta();
        this.horaInicio = dadosAlteracaoAluguel.horaInicio();
        this.trancaFim = dadosAlteracaoAluguel.trancaFim();
        this.horaFim = dadosAlteracaoAluguel.horaFim();
        this.cobranca = dadosAlteracaoAluguel.cobranca();
        this.ciclista = dadosAlteracaoAluguel.ciclista();
        this.trancaInicio = dadosAlteracaoAluguel.trancaInicio();
    }

    public int getBicicleta() {
        return bicicleta;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public int getTrancaFim() {
        return trancaFim;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public int getCobranca() {
        return cobranca;
    }

    public int getCiclista() {
        return ciclista;
    }

    public int getTrancaInicio() {
        return trancaInicio;
    }
}
