package com.aluguel.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BicicletaDTOTest {

    @Test
    void testBicicletaDTO() {
        BicicletaDTO bicicletaDTO = new BicicletaDTO("6", "Caloi", "100", "2021", "1", "Disponível");

        Assertions.assertEquals("6", bicicletaDTO.id());
        Assertions.assertEquals("Caloi", bicicletaDTO.marca());
        Assertions.assertEquals("100", bicicletaDTO.modelo());
        Assertions.assertEquals("2021", bicicletaDTO.ano());
        Assertions.assertEquals("1", bicicletaDTO.numero());
        Assertions.assertEquals("Disponível", bicicletaDTO.status());
    }
}
