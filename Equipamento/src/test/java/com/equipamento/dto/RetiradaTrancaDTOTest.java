package com.equipamento.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetiradaTrancaDTOTest {
    @Test
    void testConstructor() {
        int idTotem = 1;
        int idTranca = 1;
        int idFuncionario = 1;
        String statusAcaoReparador = "APOSENTADA";

        RetiradaTrancaDTO dadosRetirada = new RetiradaTrancaDTO(idTranca, idTotem, idFuncionario, statusAcaoReparador);

        assertEquals(idTranca, dadosRetirada.idTranca());
        assertEquals(idTotem, dadosRetirada.idTotem());
        assertEquals(idFuncionario, dadosRetirada.idFuncionario());
        assertEquals(statusAcaoReparador, dadosRetirada.statusAcaoReparador());
    }
}
