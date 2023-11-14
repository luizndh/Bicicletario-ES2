package com.aluguel.DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrancaDTOTest {

    @Test
    public void testTrancaDTO() {
        TrancaDTO trancaDTO = new TrancaDTO(1, 2, 3, "Localização", "2021", "Modelo", "Disponível");
        Assertions.assertEquals(1, trancaDTO.id());
        Assertions.assertEquals(2, trancaDTO.bicicleta());
        Assertions.assertEquals(3, trancaDTO.numero());
        Assertions.assertEquals("Localização", trancaDTO.localizacao());
        Assertions.assertEquals("2021", trancaDTO.anoDeFabricacao());
        Assertions.assertEquals("Modelo", trancaDTO.modelo());
        Assertions.assertEquals("Disponível", trancaDTO.status());
    }
}
