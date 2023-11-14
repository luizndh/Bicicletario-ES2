package com.equipamento.model;

import com.equipamento.dto.BicicletaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BicicletaTest {

    private Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste","modelo teste","2021",1, "DISPONIVEL");
        bicicleta = new Bicicleta(bicicletaDTO);
    }

    @Test
    void testGetId() { assertEquals(Bicicleta.bicicletas.size()+1, bicicleta.getId()); }

    @Test
    void testGetStatus() { assertEquals(Bicicleta.StatusBicicleta.DISPONIVEL, bicicleta.getStatus()); }

    @Test
    void testAtualizaBicicleta() {
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste","modelo teste","2021",1, "DISPONIVEL");
        bicicleta.atualizaBicicleta(bicicletaDTO);

        assertEquals("DISPONIVEL", bicicleta.getStatus().toString());
    }
}
