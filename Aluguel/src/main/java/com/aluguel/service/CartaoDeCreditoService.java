package com.aluguel.service;

import com.aluguel.DTO.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;

import java.util.List;

import static com.aluguel.model.CartaoDeCredito.cartoesDeCreditos;;

public class CartaoDeCreditoService {
    public CartaoDeCredito recuperaCartaoDeCreditoPorId(int idCiclista) {
        if(!cartoesDeCreditos.isEmpty()) {
            for (CartaoDeCredito cartaoDeCredito : cartoesDeCreditos) {
                if (cartaoDeCredito.getId() == idCiclista) {
                    return cartaoDeCredito;
                }
            }
        }
        throw new IllegalArgumentException("O cartao de crédito com id " + idCiclista + " não existe");
    }

    public List<CartaoDeCredito> recuperaCartoesDeCreditos() { return cartoesDeCreditos; }

    public CartaoDeCredito alteraCartaoDeCredito(int idCiclista, CartaoDeCreditoDTO dadosAlteracaoCartaoDeCredito) {
        if(!cartoesDeCreditos.isEmpty()) {
            for (CartaoDeCredito cartaoDeCredito : cartoesDeCreditos) {
                if (cartaoDeCredito.getId() == idCiclista) {
                    cartaoDeCredito.atualizaCartaoDeCredito(dadosAlteracaoCartaoDeCredito);
                    return cartaoDeCredito;
                }
            }
        }
        throw new IllegalArgumentException("O cartao de crédito com id " + idCiclista + " não existe");
    }
}

