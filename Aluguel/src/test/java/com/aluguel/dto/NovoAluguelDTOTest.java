package com.aluguel.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NovoAluguelDTOTest {

    @Test
    public void testNovoAluguelDTO() {
        // Arrange
        String ciclista = "ciclistaTest";
        String trancaInicio = "trancaTest";

        // Act
        NovoAluguelDTO novoAluguelDTO = new NovoAluguelDTO(ciclista, trancaInicio);

        // Assert
        Assertions.assertEquals(ciclista, novoAluguelDTO.ciclista());
        Assertions.assertEquals(trancaInicio, novoAluguelDTO.trancaInicio());
    }
}