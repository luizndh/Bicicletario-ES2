package com.equipamento.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InclusaoTrancaDTOTest {

    @Test
    public void testConstructor() {
        int idTotem = 1;
        int idTranca = 1;
        int idFuncionario = 1;

        InclusaoTrancaDTO dadosInclusao = new InclusaoTrancaDTO(idTotem, idTranca, idFuncionario);

        assertEquals(idTotem, dadosInclusao.idTotem());
        assertEquals(idTranca, dadosInclusao.idTranca());
        assertEquals(idFuncionario, dadosInclusao.idFuncionario());
    }
}
