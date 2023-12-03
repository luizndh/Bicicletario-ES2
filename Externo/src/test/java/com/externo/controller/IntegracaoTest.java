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

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/restaurarDados"))
                    .GET()
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void recuperaEmailDeCiclistaPorIdCorreto() throws Exception {
        // Arrange
        String emailEsperado = "user@example.com";
        String idCiclista = "1";

        // Act
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/ciclista/" + idCiclista))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CiclistaResponseDTO ciclistaResponse = mapper.readValue(response.body(), CiclistaResponseDTO.class);

        // Assert
        assertEquals(200, response.statusCode());
        assertEquals(emailEsperado, ciclistaResponse.email());
    }

    @Test
    void recuperaEmailDeCiclistaPorIdNotFound() throws Exception {
        // Arrange
        String idCiclista = "50";

        // Act
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/ciclista/" + idCiclista))
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

    @Test
    void recuperaEmailDeCiclistaPorIdRequisicaoMalFormatada() throws Exception {
        // Arrange

        // Act
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/ciclista/"))
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
    void recuperaCartaoDeCreditoPorIdCorreto() throws Exception {
        // Arrange
        String numeroEsperado = "4012001037141112";
        String validadeEsperada = "12/2022";
        String cvvEsperado = "123";
        String idCiclista = "1";

        // Act
        ObjectMapper mapper = new ObjectMapper();

        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/cartaoDeCredito/" + idCiclista))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CartaoDeCreditoResponseDTO cartaoResponse = mapper.readValue(response.body(), CartaoDeCreditoResponseDTO.class);

        // Assert
        assertEquals(200, response.statusCode());
        assertEquals(numeroEsperado, cartaoResponse.numero());
        assertEquals(validadeEsperada, cartaoResponse.validade());
        assertEquals(cvvEsperado, cartaoResponse.cvv());
    }

    @Test
    void recuperaCartaoDeCreditoPorIdNotFound() throws Exception {
        // Arrange
        String idCiclista = "50";

        // Act
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/cartaoDeCredito/" + idCiclista))
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
    void recuperaCartaoDeCreditoPorIdDadosInvalidos() throws Exception {
        // Arrange

        // Act
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/cartaoDeCredito/"))
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