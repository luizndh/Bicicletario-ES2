package com.aluguel.model;

import java.util.ArrayList;
import java.util.List;

import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.NovoAluguelDTO;

public class Aluguel {

    private int bicicleta;
    private String horaInicio;
    private int trancaFim;
    private String horaFim;
    private int cobranca;
    private int ciclista;
    private int trancaInicio;

    public static final List<Aluguel> alugueis = new ArrayList<>();

    public Aluguel(NovoAluguelDTO dadosCadastroAluguel) {
        this.ciclista = Integer.parseInt(dadosCadastroAluguel.ciclista());
        this.trancaInicio = Integer.parseInt(dadosCadastroAluguel.trancaInicio());
    }

    public void atualizaAluguel(AluguelDTO dadosAlteracaoAluguel) {
        this.bicicleta = Integer.parseInt(dadosAlteracaoAluguel.bicicleta());
        this.horaInicio = dadosAlteracaoAluguel.horaInicio();
        this.trancaFim = Integer.parseInt(dadosAlteracaoAluguel.trancaFim());
        this.horaFim = dadosAlteracaoAluguel.horaFim();
        this.cobranca = Integer.parseInt(dadosAlteracaoAluguel.cobranca());
        this.ciclista = Integer.parseInt(dadosAlteracaoAluguel.ciclista());
        this.trancaInicio = Integer.parseInt(dadosAlteracaoAluguel.trancaInicio());
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
