package com.equipamento.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrancaDTOTest {
    @Test
    public void testConstructor() {
        int numero = 1;
        String localizacao = "localizacao teste";
        String anoDeFabricacao = "2017";
        String modelo = "modelo teste";
        String status = "NOVA";

        TrancaDTO trancaDTO = new TrancaDTO(numero, localizacao, anoDeFabricacao, modelo, status);

        assertEquals(numero, trancaDTO.numero());
        assertEquals(localizacao, trancaDTO.localizacao());
        assertEquals(anoDeFabricacao, trancaDTO.anoDeFabricacao());
        assertEquals(modelo, trancaDTO.modelo());
        assertEquals(status, trancaDTO.status());
    }
}
