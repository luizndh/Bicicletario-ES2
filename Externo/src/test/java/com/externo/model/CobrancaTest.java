package com.externo.model;

import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca.StatusCobranca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CobrancaTest {
    private Cobranca cobranca;

    @BeforeEach
    public void setUp() {
        CobrancaDTO cobrancaDTO = new CobrancaDTO(StatusCobranca.PENDENTE.toString(), "01/01/2023 12:00", "01/01/2023 13:00", 10L, 2);
        cobranca = new Cobranca(cobrancaDTO);
    }

    @Test
    public void testAtualizaCobranca() {
        CobrancaDTO cobrancaDTO2 = new CobrancaDTO(StatusCobranca.PENDENTE.toString(), "02/01/2023 12:00", "02/01/2023 13:40", 1500L, 3);
        cobranca.atualizaCobranca(cobrancaDTO2);

        assertEquals(StatusCobranca.PENDENTE, cobranca.getStatus());
        assertEquals("02/01/2023 12:00", cobranca.getHoraSolicitacao());
        assertEquals("02/01/2023 13:40", cobranca.getHoraFinalizacao());
        assertEquals(1500L, cobranca.getValor());
        assertEquals(3, cobranca.getCiclista());
    }

    //testes dos getters e setters de cobranca:
    @Test
    public void testGetId() {
        assertEquals(Cobranca.cobrancas.size()+1, cobranca.getId());
    }

    @Test
    public void testGetStatus() {
        assertEquals(StatusCobranca.PENDENTE, cobranca.getStatus());
    }

    @Test
    public void testGetHoraSolicitacao() {
        assertEquals("01/01/2023 12:00", cobranca.getHoraSolicitacao());
    }

    @Test
    public void testGetHoraFinalizacao() {
        assertEquals("01/01/2023 13:00", cobranca.getHoraFinalizacao());
    }

    @Test
    public void testGetValor() {
        assertEquals(10.00f, cobranca.getValor());
    }

    @Test
    public void testGetCiclista() {
        assertEquals(2, cobranca.getCiclista());
    }
}
