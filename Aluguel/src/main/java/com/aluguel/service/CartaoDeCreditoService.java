package com.aluguel.service;

import com.aluguel.Integracoes;
import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.model.Ciclista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aluguel.model.CartaoDeCredito.cartoesDeCreditos;

@Service
public class CartaoDeCreditoService {

    @Autowired
    Integracoes integracoes;

    @Autowired
    CiclistaService ciclistaService;

    public void restauraDados() {
        cartoesDeCreditos.clear();

        cartoesDeCreditos.add(new CartaoDeCredito(new CartaoDeCreditoDTO("Fulano Beltrano", "4012001037141112", "12/2022", "132")));
        cartoesDeCreditos.add(new CartaoDeCredito(new CartaoDeCreditoDTO("Fulano Beltrano", "4012001037141112", "12/2022", "132")));
        cartoesDeCreditos.add(new CartaoDeCredito(new CartaoDeCreditoDTO("Fulano Beltrano", "4012001037141112", "12/2022", "132")));
        cartoesDeCreditos.add(new CartaoDeCredito(new CartaoDeCreditoDTO("Fulano Beltrano", "4012001037141112", "12/2022", "132")));
    }

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
        Ciclista ciclista = ciclistaService.recuperaCiclistaPorId(idCiclista);
        CartaoDeCredito cartaoDeCredito = recuperaCartaoDeCreditoPorId(idCiclista);

        integracoes.validaCartaoDeCredito(dadosAlteracaoCartaoDeCredito);

        cartaoDeCredito.atualizaCartaoDeCredito(dadosAlteracaoCartaoDeCredito);

        integracoes.enviaEmail(ciclista.getEmail(), "Dados de Cartão de Crédito alterados",
               "Olá " + ciclista.getNome() + ",\n\n" +
               "Os dados de seu cartão de crédito foram alterados com sucesso!\n\n" +
               "Atenciosamente,\n" +
               "Equipe Aluguel de Bicicletas");
        
        return cartaoDeCredito;
    }
}

