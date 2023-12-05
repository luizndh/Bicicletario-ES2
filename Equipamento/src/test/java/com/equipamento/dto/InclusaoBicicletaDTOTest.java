package com.equipamento.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InclusaoBicicletaDTOTest {

    @Test
    void testConstructor() {
        int idTranca = 1;
        int idBicicleta = 1;
        int idFuncionario = 1;


        InclusaoBicicletaDTO dadosInclusao = new InclusaoBicicletaDTO(idTranca, idBicicleta, idFuncionario);

        assertEquals(idTranca, dadosInclusao.idTranca());
        assertEquals(idBicicleta, dadosInclusao.idBicicleta());
        assertEquals(idFuncionario, dadosInclusao.idFuncionario());

    }

}
