package com.aluguel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.service.CartaoDeCreditoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartaoDeCreditoControllerTest {

    @MockBean
    CartaoDeCreditoService cartaoDeCreditoService;

    @Autowired
    private MockMvc mvc;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @Test
    public void recuperaCartaoDeCreditoPorId() throws Exception {
        // Arrange
        when(cartaoDeCreditoService.recuperaCartaoDeCreditoPorId(1)).thenReturn(new CartaoDeCredito(new CartaoDeCreditoDTO("Luis Fumado", "1234567890123456", "12/2025", "123")));

        String json = "{\"id\":1,\"nomeTitular\":\"Luis Fumado\",\"numero\":\"1234567890123456\",\"validade\":\"12/2025\",\"cvv\":\"123\"}";

        // Act
        var response = this.mvc.perform(get("/cartaoDeCredito/{idCiclista}", 1)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    public void alteraCartaoDeCredito() throws Exception {
        // Arrange
        when(cartaoDeCreditoService.alteraCartaoDeCredito(1, new CartaoDeCreditoDTO("Luis Fumado", "1234567890123456", "12/2025", "123"))).thenReturn(new CartaoDeCredito(new CartaoDeCreditoDTO("Luis Fumado", "1234567890123456", "12/2025", "123")));

        String jsonEntrada = """
                {
                    "nomeTitular": "Luis Fumado",
                    "numero": "1234567890123456",
                    "validade": "12/2025",
                    "cvv": "123"
                }
                """;

        String json = "{\"id\":1,\"nomeTitular\":\"Luis Fumado\",\"numero\":\"1234567890123456\",\"validade\":\"12/2025\",\"cvv\":\"123\"}";

        // Act
        var response = this.mvc.perform(put("/cartaoDeCredito/{idCiclista}", 1).content(jsonEntrada).contentType("application/json")).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }
}