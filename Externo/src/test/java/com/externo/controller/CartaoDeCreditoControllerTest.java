package com.externo.controller;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.servico.CartaoDeCreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class CartaoDeCreditoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    CartaoDeCreditoService cartaoDeCreditoService;

    CartaoDeCreditoDTO cartao;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        cartao = new CartaoDeCreditoDTO("Marcos da Silva", "9182736451230192", "02/2028","987");
    }

    @Test
    void testvalidaCartaoDeCreditoCorreto() throws Exception {
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
        String json = "{\"nomeTitular\":\"Marcos da Silva\",\"numero\":\"9182736451230192\",\"validade\":\"02/2028\",\"cvv\":\"987\"}";

        when(cartaoDeCreditoService.validaCartaoDeCredito(cartaoDTO)).thenReturn(Map.of("codigo", "200", "mensagem", "Cartao Valido"));

        // Act
        var response = this.mvc.perform(post("/validaCartaoDeCredito")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        //assertEquals(json, response.getContentAsString());
    }

    @Test
    void testvalidaCartaoDeCreditoIncorreto() throws Exception {
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
