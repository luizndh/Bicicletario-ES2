package com.externo.model;

import com.externo.DTO.EmailDTO;
import com.externo.model.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmailTest {

    private Email email;

    @BeforeEach
    public void setUp() {
        EmailDTO dadosCadastroEmail = new EmailDTO("joaodasneves@gmail.com", "Bom dia", "Bom dia meu querido, como vai?");
        email = new Email(dadosCadastroEmail);
    }

    @Test
    public void testAtualizaEmail() {
        EmailDTO dadosAlteracaoEmail = new EmailDTO("joaodasneves2023@gmail.com", "Boa tarde", "Boa tarde meu querido, como vai?");

        email.atualizaEmail(dadosAlteracaoEmail);

        assertEquals("joaodasneves2023@gmail.com", email.getEmail());
        assertEquals("Boa tarde", email.getAssunto());
        assertEquals("Boa tarde meu querido, como vai?", email.getMensagem());
    }

    //testes dos getters de email:
    @Test
    public void testGetId() {
        assertEquals(Email.emails.size()+1, email.getId());
    }

    @Test
    public void testGetEmail() {
        assertEquals("joaodasneves2023@gmail.com", email.getEmail());
    }

    @Test
    public void testGetAssunto() {
        assertEquals("Boa tarde", email.getAssunto());
    }

    @Test
    public void testGetMensagem() {
        assertEquals("Boa tarde meu querido, como vai?", email.getMensagem());
    }
}
