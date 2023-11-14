package com.equipamento.model;

import com.equipamento.DTO.BicicletaDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BicicletaTest {

    private Bicicleta bicicleta;

    @BeforeEach
    public void setUp() {
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste","modelo teste","2021",1, "DISPONIVEL");
        bicicleta = new Bicicleta(bicicletaDTO);
    }

    @Test
    public void testGetId() { assertEquals(Bicicleta.bicicletas.size()+1, bicicleta.getId()); }

    @Test
    public void testGetStatus() { assertEquals(Bicicleta.StatusBicicleta.DISPONIVEL, bicicleta.getStatus()); }

    @Test
    public void testAtualizaBicicleta() {
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste","modelo teste","2021",1, "DISPONIVEL");
        bicicleta.atualizaBicicleta(bicicletaDTO);

        assertEquals("DISPONIVEL", bicicleta.getStatus().toString());
    }
}
