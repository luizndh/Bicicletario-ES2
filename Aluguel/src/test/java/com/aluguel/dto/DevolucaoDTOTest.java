package com.aluguel.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DevolucaoDTOTest {

    @Test
    void testDevolucaoDTO() {
        DevolucaoDTO devolucaoDTO = new DevolucaoDTO("1", "10:00", "2", "11:00", "10", "3");

        assertEquals("1", devolucaoDTO.bicicleta());
        assertEquals("10:00", devolucaoDTO.horaInicio());
        assertEquals("2", devolucaoDTO.trancaFim());
        assertEquals("11:00", devolucaoDTO.horaFim());
        assertEquals("10", devolucaoDTO.cobranca());
        assertEquals("3", devolucaoDTO.ciclista());
    }
}
