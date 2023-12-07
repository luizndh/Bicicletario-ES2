package com.aluguel.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.aluguel.model.Passaporte;

public class NovoCiclistaDTOTest {

    @Test
    public void testNovoCiclistaDTO() {
        NovoCiclistaDTO novoCiclistaDTO = new NovoCiclistaDTO(
                "John Doe",
                "1990-01-01",
                "123456789",
                new Passaporte("aiai", "uiui", "receba"),
                "USA",
                "johndoe@example.com",
                "12345678"
        );

        Assertions.assertEquals("John Doe", novoCiclistaDTO.nome());
        Assertions.assertEquals("1990-01-01", novoCiclistaDTO.nascimento());
        Assertions.assertEquals("123456789", novoCiclistaDTO.cpf());
        Assertions.assertEquals("USA", novoCiclistaDTO.nacionalidade());
        Assertions.assertEquals("johndoe@example.com", novoCiclistaDTO.email());
    }
}