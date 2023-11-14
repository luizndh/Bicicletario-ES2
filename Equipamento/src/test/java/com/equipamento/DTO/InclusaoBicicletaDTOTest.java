package com.equipamento.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InclusaoBicicletaDTOTest {

    @Test
    public void testConstructor() {
        int idTotem = 1;
        int idBicicleta = 1;
        int idFuncionario = 1;


        InclusaoBicicletaDTO dadosInclusao = new InclusaoBicicletaDTO(idTotem, idBicicleta, idFuncionario);

        assertEquals(idTotem, dadosInclusao.idTotem());
        assertEquals(idBicicleta, dadosInclusao.idBicicleta());
        assertEquals(idFuncionario, dadosInclusao.idFuncionario());

    }

}
