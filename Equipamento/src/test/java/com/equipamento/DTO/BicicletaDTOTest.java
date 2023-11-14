package com.equipamento.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BicicletaDTOTest {

    @Test
    public void testConstructor() {
        String marca = "marca teste";
        String modelo = "modelo teste";
        String ano = "2015";
        int numero = 10;
        String status = "OCUPADA";

        BicicletaDTO bicicletaDTO = new BicicletaDTO(marca, modelo, ano, numero, status);

        assertEquals(marca, bicicletaDTO.marca());
        assertEquals(modelo, bicicletaDTO.modelo());
        assertEquals(ano, bicicletaDTO.ano());
        assertEquals(numero, bicicletaDTO.numero());
        assertEquals(status, bicicletaDTO.status());
    }
}
