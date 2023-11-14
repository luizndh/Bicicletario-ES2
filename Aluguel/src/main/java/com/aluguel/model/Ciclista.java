package com.aluguel.model;

import com.aluguel.DTO.CiclistaDTO;

import java.util.ArrayList;
import java.util.List;

public class Ciclista {

    private int id;
    private StatusCiclista status;
    private String nome;
    private String nascimento;
    private String cpf;
    private Passaporte passaporte;
    private String nacionalidade;
    private String email;
    private String urlFotoDocumento;
    
    public static List<Ciclista> ciclistas = new ArrayList<>();

    public Ciclista(CiclistaDTO dadosCadastroCiclista) {
        this.id = ciclistas.size() + 1;
        this.status = dadosCadastroCiclista.status();
        this.nome = dadosCadastroCiclista.nome();
        this.nascimento = dadosCadastroCiclista.nascimento();
        this.cpf = dadosCadastroCiclista.cpf();
        this.passaporte = dadosCadastroCiclista.passaporte();
        this.nacionalidade = dadosCadastroCiclista.nacionalidade();
        this.email = dadosCadastroCiclista.email();
        this.urlFotoDocumento = dadosCadastroCiclista.urlFotoDocumento();
    }

    public void atualizaCiclista(CiclistaDTO dadosAlteracaoCiclista) {
        this.nome = dadosAlteracaoCiclista.nome();
        this.nascimento = dadosAlteracaoCiclista.nascimento();
        this.cpf = dadosAlteracaoCiclista.cpf();
        this.passaporte = dadosAlteracaoCiclista.passaporte();
        this.nacionalidade = dadosAlteracaoCiclista.nacionalidade();
        this.email = dadosAlteracaoCiclista.email();
        this.urlFotoDocumento = dadosAlteracaoCiclista.urlFotoDocumento();
    }

    public enum StatusCiclista {
        ATIVO, 
        INATIVO, 
        AGUARDANDO_CONFIRMACAO
    }

    public int getId() {
        return this.id;
    }

    public StatusCiclista getStatus() {
        return status;
    }

    public boolean isAtivo() {
        return this.status == StatusCiclista.ATIVO;
    }

    public void ativaCiclista() {
        this.status = StatusCiclista.ATIVO;
    }

    public String getNome() {
        return nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public String getEmail() {
        return email;
    }

    public String getUrlFotoDocumento() {
        return urlFotoDocumento;
    }
    
}
