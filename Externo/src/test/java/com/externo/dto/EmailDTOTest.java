package com.externo.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmailDTOTest {

    @Test
    public void testEmailDTO() {
        // Arrange
        String email = "joaozinho@gmail.com";
        String assunto = "Conta de luz";
        String mensagem = "A conta de luz está atrasada, favor pagar até o dia correto";

        // Act
        EmailDTO emailDTO = new EmailDTO(email, assunto, mensagem);

        // Assert
        Assertions.assertEquals(email, emailDTO.email());
        Assertions.assertEquals(assunto, emailDTO.assunto());
        Assertions.assertEquals(mensagem, emailDTO.mensagem());
    }
}