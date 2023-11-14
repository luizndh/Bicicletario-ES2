package com.equipamento.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetiradaBicicletaDTOTest {

    @Test
    void testConstructor() {
        int idTranca = 1;
        int idBicicleta = 1;
        int idFuncionario = 1;
        String statusAcaoReparador = "APOSENTADA";

        RetiradaBicicletaDTO dadosRetirada = new RetiradaBicicletaDTO(idTranca, idBicicleta, idFuncionario, statusAcaoReparador);

        assertEquals(idTranca, dadosRetirada.idTranca());
        assertEquals(idBicicleta, dadosRetirada.idBicicleta());
        assertEquals(idFuncionario, dadosRetirada.idFuncionario());
        assertEquals(statusAcaoReparador, dadosRetirada.statusAcaoReparador());
    }
}
