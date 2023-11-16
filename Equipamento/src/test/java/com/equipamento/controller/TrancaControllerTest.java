package com.equipamento.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.equipamento.dto.TrancaDTO;
import com.equipamento.model.Tranca;
import com.equipamento.servico.TrancaService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.NoSuchElementException;


@SpringBootTest
@AutoConfigureMockMvc
public class TrancaControllerTest {
    
    @Autowired
    private MockMvc mvc; 

    @MockBean
    TrancaService trancaService;

    static Tranca t;

    @BeforeAll
    static void setUp() {
        t = new Tranca(new TrancaDTO(1, "loc teste", "ano teste", "modelo teste", "NOVA"));
    }

    @Test
    void testRecuperaTrancasArrayVazio() throws Exception {
        // Arrange

        // Act
        var response = this.mvc.perform(get("/tranca"))
            .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testRecuperaTrancasArrayNaoVazio() throws Exception {
        // Arrange
        when(trancaService.recuperaTrancas()).thenReturn(Arrays.asList(t));
        String json = "[{\"id\":1,\"bicicleta\":0,\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}]";

        // Act
        var response = this.mvc.perform(get("/tranca"))
            .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRecuperaTrancaPorIdInvalido() throws Exception {
        // Arrange
        when(trancaService.recuperaTrancaPorId(-2)).thenThrow(IllegalArgumentException.class);
        String json = "{\"codigo\":422,\"mensagem\":null}";

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}", -2))
            .andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRecuperaTrancaQueNaoExiste() throws Exception {
        // Arrange
        when(trancaService.recuperaTrancaPorId(500)).thenThrow(NoSuchElementException.class);
                String json = "{\"codigo\":404,\"mensagem\":null}";

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}", 500))
            .andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRecuperaTrancaPorIdValidoQueExiste() throws Exception {
        // Arrange
        when(trancaService.recuperaTrancaPorId(1)).thenReturn(t);
        String json = "{\"id\":1,\"bicicleta\":0,\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}", 1))
            .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testCadastraNovaTrancaDadosCorretos() throws Exception {
        // Arrange
        TrancaDTO dto = new TrancaDTO(1, "teste", "2020", "teste", "NOVA");
        when(trancaService.cadastraTranca(dto)).thenReturn(new Tranca(dto));

        String jsonEntrada = """
                {
                    "numero": 1,
                    "localizacao": "teste",
                    "anoDeFabricacao": "2020",
                    "modelo": "teste",
                    "status": "NOVA"
                }
                """;
        String jsonResposta = "{\"id\":1,\"bicicleta\":0,\"modelo\":\"teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(post("/tranca")
            .content(jsonEntrada)
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }
}
