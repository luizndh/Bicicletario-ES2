package com.example.equipamento.model;

import java.util.List;

public class Tranca {
    private int id;
    private int bicicleta;
    private int numero;
    private String localizacao;
    private String anoDeFabricacao;
    private String modelo;
    private StatusTranca status;

    // Mock
    public static List<Tranca> trancas;

    public int getId() {
        return this.id;
    }

    public Tranca() {}
    public Tranca(int id) {
        this.id = id;
    }

    public Tranca(int id, int bicicleta, int numero, String localizacao, String anoDeFabricacao, String modelo, StatusTranca status) {
        this.id = id;
        this.bicicleta = bicicleta;
        this.numero = numero;
        this.localizacao = localizacao;
        this.anoDeFabricacao = anoDeFabricacao;
        this.modelo = modelo;
        this.status = status;
    }
}
