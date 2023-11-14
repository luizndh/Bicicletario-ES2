package com.aluguel.service;

import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;

import java.util.List;

import static com.aluguel.model.CartaoDeCredito.cartoesDeCreditos;

public class CartaoDeCreditoService {
    public CartaoDeCredito recuperaCartaoDeCreditoPorId(int idCiclista) {
        for (CartaoDeCredito cartaoDeCredito : cartoesDeCreditos) {
            if (cartaoDeCredito.getId() == idCiclista) {
                return cartaoDeCredito;
            }
        }
        throw new IllegalArgumentException("O cartao de crédito com id " + idCiclista + " não existe");
    }

    public List<CartaoDeCredito> recuperaCartoesDeCreditos() { return cartoesDeCreditos; }

    public CartaoDeCredito alteraCartaoDeCredito(int idCiclista, CartaoDeCreditoDTO dadosAlteracaoCartaoDeCredito) {
        CartaoDeCredito cartaoDeCredito = recuperaCartaoDeCreditoPorId(idCiclista);
        
        // TODO: validar cartão junto a administradora CC
        ///validaCartaoDeCredito

        // TODO: enviar email para o ciclista informando que os dados de seu cartão de crédito foram alterados
            ///enviarEmail(ciclista.getEmail(), "Dados de Cartão de Crédito alterados",
            //        "Olá " + ciclista.getNome() + ",\n\n" +
            //        "Os dados de seu cartão de crédito foram alterados com sucesso!\n\n" +
            //        "Atenciosamente,\n" +
            //        "Equipe Aluguel de Bicicletas");
        
        cartaoDeCredito.atualizaCartaoDeCredito(dadosAlteracaoCartaoDeCredito);
        return cartaoDeCredito;
    }
}

