package com.aluguel.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AluguelDTOTest {

    @Test
    void testAluguelDTO() {
        AluguelDTO aluguelDTO = new AluguelDTO("1", "10:00", "2", "11:00", "50", "3", "4", "EM_ANDAMENTO");
        
        Assertions.assertEquals("1", aluguelDTO.bicicleta());
        Assertions.assertEquals("10:00", aluguelDTO.horaInicio());
        Assertions.assertEquals("2", aluguelDTO.trancaFim());
        Assertions.assertEquals("11:00", aluguelDTO.horaFim());
        Assertions.assertEquals("50", aluguelDTO.cobranca());
        Assertions.assertEquals("3", aluguelDTO.ciclista());
        Assertions.assertEquals("4", aluguelDTO.trancaInicio());
    }
}