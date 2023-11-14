package com.aluguel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aluguel.dto.DevolucaoDTO;
import org.junit.jupiter.api.Test;

public class DevolucaoTest {

    @Test
    void testDevolucao() {
        DevolucaoDTO dadosCadastroDevolucao = new DevolucaoDTO(1, "10:00", 2, "11:00", 10, 1);

        Devolucao devolucao = new Devolucao(dadosCadastroDevolucao);

        assertEquals(1, devolucao.getBicicleta());
        assertEquals("10:00", devolucao.getHoraInicio());
        assertEquals(2, devolucao.getTrancaFim());
        assertEquals("11:00", devolucao.getHoraFim());
        assertEquals(10, devolucao.getCobranca());
        assertEquals(1, devolucao.getCiclista());
    }
}
