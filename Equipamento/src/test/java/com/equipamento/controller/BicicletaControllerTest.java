package com.equipamento.controller;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.InclusaoBicicletaDTO;
import com.equipamento.dto.RetiradaBicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.servico.BicicletaService;
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
public class BicicletaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    BicicletaService bicicletaService;

    Bicicleta b;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @BeforeEach
    void setUp() {
        b = new Bicicleta(new BicicletaDTO("marca teste", "modelo teste", "ano teste", 1, "NOVA"));
    }

    @Test
    void testRecuperaBicicletasArrayVazio() throws Exception {
        // Arrange

        // Act
        var response = this.mvc.perform(get("/bicicleta"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testRecuperaBicicletasArrayNaoVazio() throws Exception {
        // Arrange
        when(bicicletaService.recuperaBicicletas()).thenReturn(Arrays.asList(b));
        String json = "[{\"id\":1,\"marca\":\"marca teste\",\"modelo\":\"modelo teste\",\"ano\":\"ano teste\",\"numero\":1,\"status\":\"NOVA\"}]";

        // Act
        var response = this.mvc.perform(get("/bicicleta"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRecuperaBicicletaPorIdValido() throws Exception {
        // Arrange
        when(bicicletaService.recuperaBicicletaPorId(1)).thenReturn(b);
        String json = "{\"id\":1,\"marca\":\"marca teste\",\"modelo\":\"modelo teste\",\"ano\":\"ano teste\",\"numero\":1,\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(get("/bicicleta/1"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testRecuperaBicicletaPorIdInvalido() throws Exception {
        // Arrange
        when(bicicletaService.recuperaBicicletaPorId(1)).thenThrow(IllegalArgumentException.class);

        // Act
        var response = this.mvc.perform(get("/bicicleta/1"))
                .andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testRecuperaBicicletaPorIdInexistente() throws Exception {
        // Arrange
        when(bicicletaService.recuperaBicicletaPorId(1)).thenThrow(NoSuchElementException.class);

        // Act
        var response = this.mvc.perform(get("/bicicleta/1"))
                .andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
        assertEquals(JSON_ERRO_404, response.getContentAsString());
    }

    @Test
    void testCadastraNovaBicicletaDadosCorretos() throws Exception {
        // Arrange
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste", "modelo teste", "ano teste", 1, "NOVA");
        String jsonEntrada = """
                {
                    "marca": "marca teste",
                    "modelo": "modelo teste",
                    "ano": "ano teste",
                    "numero": 1,
                    "status": "NOVA"
                }
                """;
        String json = "{\"id\":1,\"marca\":\"marca teste\",\"modelo\":\"modelo teste\",\"ano\":\"ano teste\",\"numero\":1,\"status\":\"NOVA\"}";
        when(bicicletaService.cadastraBicicleta(bicicletaDTO)).thenReturn(b);

        // Act
        var response = this.mvc.perform(post("/bicicleta")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testCadastraNovaBicicletaDadosIncorretos() throws Exception {
        // Arrange
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste", "modelo teste", "ano teste", 1, "a");
        String jsonEntrada = """
                {
                    "marca": "marca teste",
                    "modelo": "modelo teste",
                    "ano": "ano teste",
                    "numero": 1,
                    "status": "a"
                }
                """;
        when(bicicletaService.cadastraBicicleta(bicicletaDTO)).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(post("/bicicleta")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testAlteraDadosValidosBicicleta() throws Exception {
        // Arrange
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste", "modelo teste", "ano teste", 1, "NOVA");
        String jsonEntrada = """
                {
                    "marca": "marca teste",
                    "modelo": "modelo teste",
                    "ano": "ano teste",
                    "numero": 1,
                    "status": "NOVA"
                }
                """;
        String json = "{\"id\":1,\"marca\":\"marca teste\",\"modelo\":\"modelo teste\",\"ano\":\"ano teste\",\"numero\":1,\"status\":\"NOVA\"}";
        when(bicicletaService.alteraBicicleta(1, bicicletaDTO)).thenReturn(b);

        // Act
        var response = this.mvc.perform(put("/bicicleta/1")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testAlteraDadosInvalidosBicicleta() throws Exception {
        // Arrange
        BicicletaDTO bicicletaDTO = new BicicletaDTO("marca teste", "modelo teste", "ano teste", 1, "a");
        String jsonEntrada = """
                {
                    "marca": "marca teste",
                    "modelo": "modelo teste",
                    "ano": "ano teste",
                    "numero": 1,
                    "status": "a"
                }
                """;
        when(bicicletaService.alteraBicicleta(1, bicicletaDTO)).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(put("/bicicleta/1")
                .content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testExcluiBicicleta() throws Exception {
        // Arrange
        doNothing().when(bicicletaService).excluiBicicleta(1);

        // Act
        var response = this.mvc.perform(delete("/bicicleta/1")).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testAlteraStatusBicicletaCorreto() throws Exception {
        // Arrange
        String json = "{\"id\":1,\"marca\":\"marca teste\",\"modelo\":\"modelo teste\",\"ano\":\"ano teste\",\"numero\":1,\"status\":\"NOVA\"}";
        when(bicicletaService.alteraStatusBicicleta(1, "NOVA")).thenReturn(b);

        // Act
        var response = this.mvc.perform(post("/bicicleta/{idTranca}/status/{acao}", 1, "NOVA")).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    void testAlteraStatusBicicletaIncorreto() throws Exception {
        // Arrange
        when(bicicletaService.alteraStatusBicicleta(1, "a")).thenThrow(new IllegalArgumentException());

        // Act
        var response = this.mvc.perform(post("/bicicleta/{idTranca}/status/{acao}", 1, "a")).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testIntegraNaRede() throws Exception {
        // Arrange
        InclusaoBicicletaDTO inclusaoDTO = new InclusaoBicicletaDTO(1, 1, 1);
        String json = """
                {
                    "idBicicleta": 1,
                    "idFuncionario": 1,
                    "idTranca": 1
                }
                """;

        // Act
        var response = this.mvc.perform(post("/bicicleta/integrarNaRede")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        verify(bicicletaService).integrarNaRede(inclusaoDTO);
    }

    @Test
    void testRetiraDaRede() throws Exception {
        // Arrange
        RetiradaBicicletaDTO retiradaDTO = new RetiradaBicicletaDTO(1, 1, 1, "DISPONIVEL");
        String json = """
                {
                    "idBicicleta": 1,
                    "idFuncionario": 1,
                    "idTranca": 1,
                    "statusAcaoReparador": "DISPONIVEL"
                }
                """;

        // Act
        var response = this.mvc.perform(post("/bicicleta/retirarDaRede")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        verify(bicicletaService).retirarDaRede(retiradaDTO);
    }
}
