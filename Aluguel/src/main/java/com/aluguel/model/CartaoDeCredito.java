package com.aluguel.model;

import com.aluguel.dto.CartaoDeCreditoDTO;

import java.util.ArrayList;
import java.util.List;

public class CartaoDeCredito {

    private int id;
    private String nomeTitular;
    private String numero;
    private String validade;
    private String cvv;
    
    public static final List<CartaoDeCredito> cartoesDeCreditos = new ArrayList<>();

    public CartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartaoDeCredito) {
        this.id = cartoesDeCreditos.size() + 1;
        this.nomeTitular = dadosCadastroCartaoDeCredito.nomeTitular();
        this.numero = dadosCadastroCartaoDeCredito.numero();
        this.validade = dadosCadastroCartaoDeCredito.validade();
        this.cvv = dadosCadastroCartaoDeCredito.cvv();
    }

    public void atualizaCartaoDeCredito(CartaoDeCreditoDTO dadosAlteracaoCartaoDeCredito) {
        this.nomeTitular = dadosAlteracaoCartaoDeCredito.nomeTitular();
        this.numero = dadosAlteracaoCartaoDeCredito.numero();
        this.validade = dadosAlteracaoCartaoDeCredito.validade();
        this.cvv = dadosAlteracaoCartaoDeCredito.cvv();
    }

    public int getId() {
        return this.id;
    }

    public String getNomeTitular() {
        return this.nomeTitular;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getValidade() {
        return this.validade;
    }

    public String getCvv() {
        return this.cvv;
    }
    
}
