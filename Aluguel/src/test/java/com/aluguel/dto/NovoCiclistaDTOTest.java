package com.aluguel.dto;

import com.aluguel.model.Passaporte;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NovoCiclistaDTOTest {

    @Test
    public void testNovoCiclistaDTO() {
        // Create a new NovoCiclistaDTO object
        NovoCiclistaDTO novoCiclistaDTO = new NovoCiclistaDTO(
                "John Doe",
                "1990-01-01",
                "123456789",
                new Passaporte("1", "12/2025", "BR"),
                "USA",
                "johndoe@example.com",
                "http://example.com/photo.jpg"
        );

        // Verify the values of the object
        Assertions.assertEquals("John Doe", novoCiclistaDTO.nome());
        Assertions.assertEquals("1990-01-01", novoCiclistaDTO.nascimento());
        Assertions.assertEquals("123456789", novoCiclistaDTO.cpf());
        Assertions.assertEquals(new Passaporte("1", "12/2025", "BR").getNumero(), novoCiclistaDTO.passaporte().getNumero());
        Assertions.assertEquals("USA", novoCiclistaDTO.nacionalidade());
        Assertions.assertEquals("johndoe@example.com", novoCiclistaDTO.email());
        Assertions.assertEquals("http://example.com/photo.jpg", novoCiclistaDTO.urlFotoDocumento());
    }
}