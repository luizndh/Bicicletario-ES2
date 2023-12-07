package com.aluguel.dto;

import org.junit.jupiter.api.Test;

import com.aluguel.model.Passaporte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CiclistaDTOTest {

    @Test
    void testCiclistaDTO() {
        // Arrange
        int id = 1;
        String status = "ATIVO";
        String nome = "Luis Fumado";
        String nascimento = "01/01/2000";
        String cpf = "123.456.789-00";
        Passaporte passaporte = new Passaporte("aiai", "uiui", "receba");
        String nacionalidade = "Brasileiro";
        String email = "luisfumado@teste.com";
        String urlFotoDocumento = "https://www.teste.com.br";
        String senha = "12345678";

        // Act
        CiclistaDTO ciclistaDTO = new CiclistaDTO(id, status, nome, nascimento, cpf, passaporte, nacionalidade, email, senha, urlFotoDocumento);

        // Assert
        assertNotNull(ciclistaDTO);
        assertEquals(status, ciclistaDTO.status());
        assertEquals(nome, ciclistaDTO.nome());
        assertEquals(nascimento, ciclistaDTO.nascimento());
        assertEquals(cpf, ciclistaDTO.cpf());
        assertEquals(nacionalidade, ciclistaDTO.nacionalidade());
        assertEquals(email, ciclistaDTO.email());
        assertEquals(urlFotoDocumento, ciclistaDTO.urlFotoDocumento());
        assertEquals(senha, ciclistaDTO.senha());
    }
}