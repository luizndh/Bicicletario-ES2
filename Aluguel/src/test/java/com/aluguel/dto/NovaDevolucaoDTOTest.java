package com.aluguel.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NovaDevolucaoDTOTest {

    @Test
    public void testNovaDevolucaoDTO() {
        // Arrange
        String idBicicleta = "idBicicletaTest";
        String trancaFim = "trancaTest";

        // Act
        NovaDevolucaoDTO novaDevolucaoDTO = new NovaDevolucaoDTO(idBicicleta, trancaFim);

        // Assert
        Assertions.assertEquals(idBicicleta, novaDevolucaoDTO.idBicicleta());
        Assertions.assertEquals(trancaFim, novaDevolucaoDTO.trancaFim());
    }
}