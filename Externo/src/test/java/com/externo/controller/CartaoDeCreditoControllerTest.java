package com.externo.controller;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.servico.CartaoDeCreditoService;

@SpringBootTest
@AutoConfigureMockMvc
public class CartaoDeCreditoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    CartaoDeCreditoService cartaoDeCreditoService;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";


    @Test
    void testValidaCartaoDeCreditoCorreto() throws Exception {
        // Arrange
        CartaoDeCreditoDTO cartaoDTO = new CartaoDeCreditoDTO("Marcos da Silva", "9182736451230192", "02/2028","987");
        String jsonEntrada = """
    {
        "nomeTitular": "Marcos da Silva",
        "numero": "9182736451230192",
        "validade": "02/2028",
        "cvv": "987"
    }
    """;

        when(cartaoDeCreditoService.validaCartaoDeCredito(cartaoDTO)).thenReturn(Map.of("codigo", "200", "mensagem", "Cartao Valido"));

        // Act
        var response = this.mvc.perform(post("/validaCartaoDeCredito")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());

    }

    @Test
    void testValidaCartaoDeCreditoIncorreto() throws Exception {
        // Arrange
        CartaoDeCreditoDTO cartaoDTO = new CartaoDeCreditoDTO("Marcos da Silva", "9182736451230192000", "02/2028","987");
        String jsonEntrada = """
    {
        "nomeTitular": "Marcos da Silva",
        "numero": "9182736451230192000",
        "validade": "02/2028",
        "cvv": "987"
    }
    """;

        when(cartaoDeCreditoService.validaCartaoDeCredito(cartaoDTO)).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(post("/validaCartaoDeCredito")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }
}
