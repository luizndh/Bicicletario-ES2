package com.equipamento.model;

import com.equipamento.DTO.TotemDTO;
import com.equipamento.DTO.TrancaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotemTest {

    private Totem totem;

    @BeforeEach
    public void setUp() {
        TotemDTO dadosCadastroTotem = new TotemDTO("localizacao teste", "descricao teste");
        totem = new Totem(dadosCadastroTotem);
    }

    @Test
    public void testGetId() {
        assertEquals(Totem.totens.size()+1, totem.getId());
    }

    @Test
    public void testGetTrancas() {
        assertEquals(0, totem.getTrancas().size());

        totem.getTrancas().add(new Tranca(new TrancaDTO(1, "localizacao teste", "2021", "modelo teste", "NOVA")));

        assertEquals(1, totem.getTrancas().size());
    }


}
