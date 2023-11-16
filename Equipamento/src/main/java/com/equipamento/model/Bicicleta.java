package com.equipamento.model;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.InclusaoBicicletaDTO;
import com.equipamento.dto.RetiradaBicicletaDTO;

import java.util.ArrayList;
import java.util.List;

public class Bicicleta {

    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private int numero;
    private StatusBicicleta status;
    private List<InclusaoBicicletaDTO> historicoInclusao;
    private List<RetiradaBicicletaDTO> historicoRetirada;

    public static List<Bicicleta> bicicletas = new ArrayList<>();

    public enum StatusBicicleta {
        DISPONIVEL,
        EM_USO,
        NOVA,
        APOSENTADA,
        REPARO_SOLICITADO,
        EM_REPARO
    }

    public Bicicleta() {}

    public Bicicleta(BicicletaDTO dadosCadastroBicicleta) {
        this.id = bicicletas.size() + 1;
        this.ano = dadosCadastroBicicleta.ano();
        this.marca = dadosCadastroBicicleta.marca();
        this.modelo = dadosCadastroBicicleta.modelo();
        this.status = StatusBicicleta.valueOf(dadosCadastroBicicleta.status());
        this.numero = dadosCadastroBicicleta.numero();
        this.historicoInclusao = new ArrayList<>();
        this.historicoRetirada = new ArrayList<>();
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

    public void setStatus(StatusBicicleta acao) {
        this.status = acao;
    }

    public StatusBicicleta getStatus() {
        return this.status;
    }

    public void adicionaRegistroNoHistoricoDeInclusao(InclusaoBicicletaDTO dadosInclusao) {
        this.historicoInclusao.add(dadosInclusao);
    }
    public void adicionaRegistroNoHistoricoDeRetirada(RetiradaBicicletaDTO dadosRetirada) {
        this.historicoRetirada.add(dadosRetirada);
    }
}
