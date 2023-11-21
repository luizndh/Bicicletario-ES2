package com.externo.controller;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.dto.EmailDTO;
import com.externo.model.Email;
import com.externo.servico.CartaoDeCreditoService;
import com.externo.servico.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    EmailService emailService;

    Email email;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        email = new Email(new EmailDTO("joaodasneves@gmail.com", "Bom dia", "Bom dia meu querido, como vai?"));
    }

    @Test
    void testenviaEmailCorreto() throws Exception {
        // Arrange
        EmailDTO emailDTO = new EmailDTO("joaodasneves@gmail.com", "Bom dia", "Bom dia meu querido, como vai?");
        String jsonEntrada = """
    {
        "email": "lucas.sferreira@edu.unirio.br",
        "assunto": "Bom dia",
        "mensagem": "Bom dia meu querido, como vai?"
    }
    """;
        String json = "{\"id\":1,\"email\":\"joaodasneves@gmail.com\",\"assunto\":\"Bom dia\",\"mensagem\":\"Bom dia meu querido, como vai?\"}";

        when(this.emailService.enviarEmail(emailDTO)).thenReturn(new Email(emailDTO));

        // Act
        var response = this.mvc.perform(post("/enviarEmail").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        //assertEquals(json, response.getContentAsString());
    }

    @Test
    void testErroEnviandoEmail() throws Exception {
        // Arrange
        EmailDTO emailDTO = new EmailDTO("aaa@aaa.com", "Bom dia", "Bom dia meu querido, como vai?");
        String jsonEntrada = """
    {
        "email": "aaa@aaa.com",
        "assunto": "Bom dia",
        "mensagem": "Bom dia meu querido, como vai?"
    }
    """;

        when(this.emailService.enviarEmail(emailDTO)).thenThrow(new NoSuchElementException());

        // Act
        var response = this.mvc.perform(post("/enviarEmail").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
        assertEquals(JSON_ERRO_404, response.getContentAsString());
    }
}
