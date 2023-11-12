package com.externo.model;

import com.externo.DTO.CartaoDeCreditoDTO;

import java.util.ArrayList;
import java.util.List;

public class CartaoDeCredito {

    private int id;
    private String nomeTitular;
    private String numero;
    private String validade;
    private String cvv;

    public static List<CartaoDeCredito> cartoesDeCredito = new ArrayList<>();

    public CartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartao) {
        this.id = cartoesDeCredito.size() + 1;
        this.nomeTitular = dadosCadastroCartao.nomeTitular();
        this.numero = dadosCadastroCartao.numero();
        this.validade = dadosCadastroCartao.validade();
        this.cvv = dadosCadastroCartao.cvv();
    }

    public void atualizaCartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartao) {
        this.nomeTitular = dadosCadastroCartao.nomeTitular();
        this.numero = dadosCadastroCartao.numero();
        this.validade = dadosCadastroCartao.validade();
        this.cvv = dadosCadastroCartao.cvv();
    }

    public int getId() {
        return this.id;
    }
}
