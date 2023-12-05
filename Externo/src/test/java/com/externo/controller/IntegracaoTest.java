package com.externo.controller;

import com.externo.dto.CartaoDeCreditoResponseDTO;
import com.externo.dto.CiclistaResponseDTO;
import com.externo.dto.CobrancaDTO;
import com.externo.dto.EmailDTO;
import com.externo.model.Cobranca;
import com.externo.model.Email;
import com.externo.servico.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegracaoTest {
    @Autowired
    private MockMvc mvc;

    final String URL = "http://ec2-54-235-47-98.compute-1.amazonaws.com:8082";

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + "/restaurarDados"))
                    .GET()
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void recuperaCiclistaPorIdCorreto() throws Exception {
        // Arrange
        String emailEsperado = "user@example.com";
        String idCiclista = "1";
        String json = "{\"id\":1,\"status\":\"AGUARDANDO_CONFIRMACAO\",\"nome\":\"Fulano Beltrano\",\"nascimento\":\"2021-05-02\",\"cpf\":\"78804034009\",\"nacionalidade\":\"Brasileiro\",\"email\":\"user@example.com\",\"urlFotoDocumento\":null,\"senha\":\"ABC123\",\"cartaoDeCredito\":{\"id\":1,\"nomeTitular\":\"Fulano Beltrano\",\"numero\":\"4012001037141112\",\"validade\":\"2022-12\",\"cvv\":\"132\"},\"ativo\":false}";

        // Act
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + "/ciclista/" + idCiclista))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CiclistaResponseDTO ciclistaResponse = mapper.readValue(response.body(), CiclistaResponseDTO.class);

        // Assert
        assertEquals(200, response.statusCode());
        assertEquals(json, response.body());
        assertEquals(emailEsperado, ciclistaResponse.email());
    }

    @Test
    void recuperaCiclistaPorIdNotFound() throws Exception {
        // Arrange
        String idCiclista = "50";

        // Act
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + "/ciclista/" + idCiclista))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(404, response.statusCode());
        assertEquals(JSON_ERRO_404, response.body());
    }

    @Test
    void recuperaCiclistaPorIdRequisicaoMalFormatada() throws Exception {
        // Arrange

        // Act
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + "/ciclista/a"))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertEquals(422, response.statusCode());
        assertEquals(JSON_ERRO_422, response.body());
    }
}