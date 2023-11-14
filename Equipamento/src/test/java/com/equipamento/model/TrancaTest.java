package com.equipamento.model;

import com.equipamento.dto.TrancaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrancaTest {

    private Tranca tranca;

    @BeforeEach
    void setUp() {
        TrancaDTO trancaDTO = new TrancaDTO(1, "localizacao teste", "2021", "modelo teste", "NOVA");
        tranca = new Tranca(trancaDTO);
    }

    @Test
    void testGetId() {
        assertEquals(Tranca.trancas.size()+1, tranca.getId());
    }

    @Test
    void testAtualizaTranca() {
        TrancaDTO trancaDTO = new TrancaDTO(1, "localizacao teste", "2021", "teste modelo", "NOVA");
        tranca.atualizaTranca(trancaDTO);

        assertEquals("teste modelo", tranca.getModelo());
    }
}
