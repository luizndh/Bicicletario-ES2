package com.aluguel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.dto.CobrancaDTO;
import com.aluguel.dto.NovaCobrancaDTO;
import com.aluguel.dto.TrancaDTO;

public class IntegracoesTest {

    @Autowired
    private Integracoes integracoes;

    @Test
    public void testEnviaEmail() {
        integracoes = new Integracoes();
        boolean result = integracoes.enviaEmail("test@example.com", "Test Subject", "Test Message");
        Assertions.assertTrue(result);
    }

    @Test
    public void testRecuperaTrancaPorId() {
        integracoes = new Integracoes();
        TrancaDTO trancaDTO = integracoes.recuperaTrancaPorId(1);
        Assertions.assertNotNull(trancaDTO);
    }

    @Test
    public void testRecuperaBicicletaPorId() {
        integracoes = new Integracoes();
        BicicletaDTO bicicletaDTO = integracoes.recuperaBicicletaPorId(1);
        Assertions.assertNotNull(bicicletaDTO);
    }

    @Test
    public void testRealizaCobranca() {
        integracoes = new Integracoes();
        NovaCobrancaDTO novaCobranca = new NovaCobrancaDTO("10", "Test User");
        CobrancaDTO cobrancaDTO = integracoes.realizaCobranca(novaCobranca);
        Assertions.assertNotNull(cobrancaDTO);
    }

    @Test
    public void testTrancaTranca() {
        integracoes = new Integracoes();
        int idTranca = 1;
        int idBicicleta = 1;
        boolean result = integracoes.trancaTranca(idTranca, idBicicleta);
        Assertions.assertTrue(result);
    }

    @Test
    public void testDestrancaTranca() {
        integracoes = new Integracoes();
        int idTranca = 1;
        int idBicicleta = 1;
        boolean result = integracoes.destrancaTranca(idTranca, idBicicleta);
        Assertions.assertTrue(result);
    }

    @Test
    public void testValidaCartaoDeCredito() {
        integracoes = new Integracoes();
        CartaoDeCreditoDTO cartaoDeCredito = new CartaoDeCreditoDTO("Test User", "1234567890123456", "01/23", "123");
        boolean result = integracoes.validaCartaoDeCredito(cartaoDeCredito);
        Assertions.assertTrue(result);
    }

    @Test
    public void testRecuperaBicicletaAlugada() {
        integracoes = new Integracoes();
        int idBicicleta = 1;
        BicicletaDTO bicicletaDTO = integracoes.recuperaBicicletaAlugada(idBicicleta);
        Assertions.assertNotNull(bicicletaDTO);
    }

    @Test
    public void testIntegrarBicicletaNaRede() {
        integracoes = new Integracoes();
        int idBicicleta = 1;
        boolean result = integracoes.integrarBicicletaNaRede(idBicicleta);
        Assertions.assertTrue(result);
    }
}
