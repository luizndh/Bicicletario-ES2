package com.equipamento.DTO;

import com.equipamento.model.Totem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotemDTOTest {
    @Test
    public void testConstructor() {
        String localizacao = "localizacao teste";
        String descricao = "descricao teste";

        TotemDTO totemDTO = new TotemDTO(localizacao, descricao);

        assertEquals(localizacao, totemDTO.localizacao());
        assertEquals(descricao, totemDTO.descricao());

    }
}
