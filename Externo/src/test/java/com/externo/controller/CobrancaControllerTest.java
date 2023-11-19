package com.externo.controller;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import com.externo.servico.CartaoDeCreditoService;
import com.externo.servico.CobrancaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//TODO na teoria pronto, mas falta testar
@ExtendWith(MockitoExtension.class)
public class CobrancaControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    CobrancaService cobrancaService;

    Cobranca cobranca;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        cobranca = new Cobranca(new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3));
        //TODO ver se é necessario
    }

    // /cobranca
    @Test
    void testCobrancaCorreta() throws Exception {
        // Arrange
        CobrancaDTO cobrancaDTO = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3);
        String jsonEntrada = """
    {
        "status": "PENDENTE",
        "horaSolicitacao": "02/01/2023 12:00",
        "horaFinalizacao": "02/01/2023 13:40",
        "valor": 15.00,
        "ciclista": 3
    }
    """;
        String json = "{\"status\":\"PENDENTE\",\"horaSolicitacao\":\"02/01/2023 12:00\",\"horaFinalizacao\":\"02/01/2023 13:40\",\"valor\":15.00,\"ciclista\":3}";


        when(this.cobrancaService.realizaCobranca(cobrancaDTO)).thenReturn(true);

        // Act
        var response = this.mvc.perform(post("/cobranca")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testCobrancaIncorreta() throws Exception {
        // Arrange
        CobrancaDTO cobrancaDTO = new CobrancaDTO("", "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3);
        String jsonEntrada = """
    {
        "status": "",
        "horaSolicitacao": "02/01/2023 12:00",
        "horaFinalizacao": "02/01/2023 13:40",
        "valor": 15.00,
        "ciclista": 3
    }
    """;

        when(this.cobrancaService.realizaCobranca(cobrancaDTO)).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(post("/cobranca").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    // /processaCobrancasEmFila
    @Test
    void testProcessaFilaCorreto() throws Exception {
        // Arrange

        // Act
        var response = this.mvc.perform(post("/processaCobrancasEmFila")).andReturn().getResponse();
        String json = "{\"status\":\"PENDENTE\",\"horaSolicitacao\":\"02/01/2023 12:00\",\"horaFinalizacao\":\"02/01/2023 13:40\",\"valor\":15.00,\"ciclista\":3}";

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testProcessaFilaIncorreto() throws Exception {
        // Arrange

        when(cobrancaService.processaCobrancasEmFila()).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(get("/processaCobrancasEmFila")).andReturn().getResponse();
        String json = "{\"status\":\"PENDENTE\",\"horaSolicitacao\":\"02/01/2023 12:00\",\"horaFinalizacao\":\"02/01/2023 13:40\",\"valor\":15.00,\"ciclista\":3}";

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    // /filaCobranca
    @Test
    void testFilaCobrancaCorreto() throws Exception {
        // Arrange
        CobrancaDTO cobrancaDTO = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3);
        String jsonEntrada = """
                {
                    "status": "PENDENTE",
                    "horaSolicitacao": "02/01/2023 12:00",
                    "horaFinalizacao": "02/01/2023 13:40",
                    "valor": 15.00,
                    "ciclista": 3
                }
                """;
        String json = "{\"status\":\"PENDENTE\",\"horaSolicitacao\":\"02/01/2023 12:00\",\"horaFinalizacao\":\"02/01/2023 13:40\",\"valor\":15.00,\"ciclista\":3}";

        when(this.cobrancaService.incluiFilaCobranca(cobrancaDTO)).thenReturn(true);

        // Act
        var response = this.mvc.perform(post("/filaCobranca")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testFilaCobrancaIncorreto() throws Exception {
        // Arrange
        CobrancaDTO cobrancaDTO = new CobrancaDTO("", "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3);
        String jsonEntrada = """
                {
                    "status": "",
                    "horaSolicitacao": "02/01/2023 12:00",
                    "horaFinalizacao": "02/01/2023 13:40",
                    "valor": 15.00,
                    "ciclista": 3
                }
                """;

        when(this.cobrancaService.incluiFilaCobranca(cobrancaDTO)).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(post("/filaCobranca").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    // /cobranca/{idCobranca}
    @Test
    void testCobrancaPorIdCorreto () throws Exception {
        // Arrange
        CobrancaDTO cobrancaDTO = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3);
        String jsonEntrada = """
                {
                    "status": "PENDENTE",
                    "horaSolicitacao": "02/01/2023 12:00",
                    "horaFinalizacao": "02/01/2023 13:40",
                    "valor": 15.00,
                    "ciclista": 3
                }
                """;
        String json = "{\"status\":\"PENDENTE\",\"horaSolicitacao\":\"02/01/2023 12:00\",\"horaFinalizacao\":\"02/01/2023 13:40\",\"valor\":15.00,\"ciclista\":3}";

        when(this.cobrancaService.obterCobranca(1)).thenReturn(cobranca);

        // Act
        var response = this.mvc.perform(get("/cobranca/1")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testCobrancaPorIdIncorretor () throws Exception {
        // Arrange
        CobrancaDTO cobrancaDTO = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "02/01/2023 12:00", "02/01/2023 13:40", 15.00f, 3);
        String jsonEntrada = """
                {
                    "status": "PENDENTE",
                    "horaSolicitacao": "02/01/2023 12:00",
                    "horaFinalizacao": "02/01/2023 13:40",
                    "valor": 15.00,
                    "ciclista": 3
                }
                """;
        String json = "{\"status\":\"PENDENTE\",\"horaSolicitacao\":\"02/01/2023 12:00\",\"horaFinalizacao\":\"02/01/2023 13:40\",\"valor\":15.00,\"ciclista\":3}";

        when(this.cobrancaService.obterCobranca(10)).thenThrow(new NoSuchElementException());

        // Act
        var response = this.mvc.perform(get("/cobranca/10")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
        assertEquals(JSON_ERRO_404, response.getContentAsString());
    }
}
