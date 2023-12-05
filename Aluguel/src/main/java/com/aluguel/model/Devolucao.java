package com.aluguel.model;

import java.util.ArrayList;
import java.util.List;

import com.aluguel.dto.DevolucaoDTO;

public class Devolucao {
    private int bicicleta;
    private String horaInicio;
    private int trancaFim;
    private String horaFim;
    private int cobranca;
    private int ciclista;

    public static final List<Devolucao> devolucoes = new ArrayList<>();

    public Devolucao(DevolucaoDTO dadosCadastroDevolucao) {
        this.bicicleta = Integer.parseInt(dadosCadastroDevolucao.bicicleta());
        this.horaInicio = dadosCadastroDevolucao.horaInicio();
        this.trancaFim = Integer.parseInt(dadosCadastroDevolucao.trancaFim());
        this.horaFim = dadosCadastroDevolucao.horaFim();
        this.cobranca = Integer.parseInt(dadosCadastroDevolucao.cobranca());
        this.ciclista = Integer.parseInt(dadosCadastroDevolucao.ciclista());
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
}
