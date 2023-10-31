package com.equipamento.model;

import com.equipamento.DTO.BicicletaDTO;

import java.util.ArrayList;
import java.util.List;

public class Bicicleta {

    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private int numero;
    private StatusBicicleta status;
    public static List<Bicicleta> bicicletas = new ArrayList<>();

    public Bicicleta(BicicletaDTO dadosCadastroBicicleta) {
        this.id = bicicletas.size() + 1;
        this.ano = dadosCadastroBicicleta.ano();
        this.marca = dadosCadastroBicicleta.marca();
        this.modelo = dadosCadastroBicicleta.modelo();
        this.status = StatusBicicleta.valueOf(dadosCadastroBicicleta.status());
        this.numero = dadosCadastroBicicleta.numero();
    }

    public void atualizaBicicleta(BicicletaDTO dadosAlteracaoBicicleta) {
        this.ano = dadosAlteracaoBicicleta.ano();
        this.marca = dadosAlteracaoBicicleta.marca();
        this.modelo = dadosAlteracaoBicicleta.modelo();
        this.status = StatusBicicleta.valueOf(dadosAlteracaoBicicleta.status());
        this.numero = dadosAlteracaoBicicleta.numero();
    }

    public int getId() {
        return this.id;
    }
}
