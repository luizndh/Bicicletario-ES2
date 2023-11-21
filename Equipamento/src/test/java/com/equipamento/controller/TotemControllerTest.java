package com.equipamento.controller;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.TotemDTO;
import com.equipamento.dto.TrancaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Totem;
import com.equipamento.model.Tranca;
import com.equipamento.servico.TotemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TotemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TotemService totemService;

    Totem t;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        t = new Totem(new TotemDTO("localizacao teste", "descricao teste"));
    }

    @Test
    void testRecuperaTotensArrayVazio() throws Exception {
        // Arrange

        // Act
        var response = this.mvc.perform(get("/totem"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testRecuperaTotensArrayNaoVazio() throws Exception {
        // Arrange
        when(totemService.recuperaTotens()).thenReturn(Arrays.asList(t));
        String json = "[{\"id\":1,\"localizacao\":\"localizacao teste\",\"descricao\":\"descricao teste\"}]";

        // Act
        var response = this.mvc.perform(get("/totem"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testCadastraTotem() throws Exception {
        // Arrange
        TotemDTO dadosCadastroTotem = new TotemDTO("localizacao teste", "descricao teste");
        Totem t = new Totem(dadosCadastroTotem);
        when(totemService.cadastraTotem(dadosCadastroTotem)).thenReturn(t);
        String jsonResposta = "{\"id\":1,\"localizacao\":\"localizacao teste\",\"descricao\":\"descricao teste\"}";
        String jsonEntrada = """
{
                    "localizacao": "localizacao teste",
                    "descricao": "descricao teste"
                }
                """;

        // Act
        var response = this.mvc.perform(post("/totem").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testCadastraTotemComDadosInvalidos() throws Exception {
        // Arrange
        TotemDTO dto = new TotemDTO("loc teste", "desc teste");
        when(totemService.cadastraTotem(dto)).thenThrow(IllegalArgumentException.class);
        String jsonEntrada = """
                {
                    "localizacao": "loc teste",
                    "descricao": "desc teste"
                }
                """;

        // Act
        var response = this.mvc.perform(post("/totem")
                .content(jsonEntrada)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testAlteraDadosTotem() throws Exception {
        // Arrange
        TotemDTO dadosCadastroTotem = new TotemDTO("localizacao teste", "descricao teste");
        Totem t = new Totem(dadosCadastroTotem);
        when(totemService.alteraTotem(1, dadosCadastroTotem)).thenReturn(t);
        String jsonResposta = "{\"id\":1,\"localizacao\":\"localizacao teste\",\"descricao\":\"descricao teste\"}";
        String jsonEntrada = """
                {
                    "localizacao": "localizacao teste",
                    "descricao": "descricao teste"
                }
                """;

        // Act
        var response = this.mvc.perform(put("/totem/1").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testAlteraDadosTotemComDadosInvalidos() throws Exception {
        // Arrange
        TotemDTO dto = new TotemDTO("loc teste", "desc teste");
        when(totemService.alteraTotem(1, dto)).thenThrow(IllegalArgumentException.class);
        String jsonEntrada = """
                {
                    "localizacao": "loc teste",
                    "descricao": "desc teste"
                }
                """;

        // Act
        var response = this.mvc.perform(put("/totem/1")
                .content(jsonEntrada)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testExcluiTotem() throws Exception {
        // Arrange
        doNothing().when(totemService).excluiTotem(1);

        // Act
        var response = this.mvc.perform(delete("/totem/{idTotem}", 1))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testRetornaTrancasCadastradasArrayNaoVazio() throws Exception {
        // Arrange
        Tranca tranca = new Tranca(new TrancaDTO(1, "localizacao teste", "2021", "modelo teste", "NOVA"));
        when(totemService.recuperaTrancasDoTotem(1)).thenReturn(Arrays.asList(tranca));
        String json = "[{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"localizacao teste\",\"anoDeFabricacao\":\"2021\",\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}]";

        // Act
        var response = this.mvc.perform(get("/totem/1/trancas"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRetornaTrancasCadastradasArrayVazio() throws Exception {
        // Arrange

        // Act
        var response = this.mvc.perform(get("/totem/1/trancas"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testRetornaBicicletasCadastradasArrayNaoVazio() throws Exception {
        // Arrange
        Bicicleta bicicleta = new Bicicleta(new BicicletaDTO("marca teste", "modelo teste", "2021", 1, "NOVA"));
        when(totemService.recuperaBicicletasDoTotem(1)).thenReturn(Arrays.asList(bicicleta));
        String json = "[{\"id\":1,\"marca\":\"marca teste\",\"modelo\":\"modelo teste\",\"ano\":\"2021\",\"numero\":1,\"status\":\"NOVA\"}]";

        // Act
        var response = this.mvc.perform(get("/totem/1/bicicletas"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRetornaBicicletasCadastradasArrayVazio() throws Exception {
        // Arrange

        // Act
        var response = this.mvc.perform(get("/totem/1/bicicletas"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }
}
