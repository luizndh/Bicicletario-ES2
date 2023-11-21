package com.aluguel.dto;

import com.aluguel.model.Passaporte;
import org.junit.jupiter.api.Test;
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
        Passaporte passaporte = new Passaporte("2345678","12/2025", "BR");
        String nacionalidade = "Brasileiro";
        String email = "luisfumado@teste.com";
        String urlFotoDocumento = "https://teste.com/foto.jpg";

        // Act
        CiclistaDTO ciclistaDTO = new CiclistaDTO(id, status, nome, nascimento, cpf, passaporte, nacionalidade, email, urlFotoDocumento);

        // Assert
        assertNotNull(ciclistaDTO);
        assertEquals(status, ciclistaDTO.status());
        assertEquals(nome, ciclistaDTO.nome());
        assertEquals(nascimento, ciclistaDTO.nascimento());
        assertEquals(cpf, ciclistaDTO.cpf());
        assertEquals(passaporte, ciclistaDTO.passaporte());
        assertEquals(nacionalidade, ciclistaDTO.nacionalidade());
        assertEquals(email, ciclistaDTO.email());
        assertEquals(urlFotoDocumento, ciclistaDTO.urlFotoDocumento());
    }
}