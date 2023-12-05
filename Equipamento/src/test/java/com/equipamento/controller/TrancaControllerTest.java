package com.equipamento.controller;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.InclusaoTrancaDTO;
import com.equipamento.dto.RetiradaTrancaDTO;
import com.equipamento.model.Bicicleta;
import org.junit.jupiter.api.BeforeEach;
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

    Tranca t;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";


    @BeforeEach
    void setUp() {
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
        String json = "[{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"loc teste\",\"anoDeFabricacao\":\"ano teste\",\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}]";

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

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}", -2))
            .andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testRecuperaTrancaQueNaoExiste() throws Exception {
        // Arrange
        when(trancaService.recuperaTrancaPorId(500)).thenThrow(NoSuchElementException.class);

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}", 500))
            .andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
        assertEquals(JSON_ERRO_404, response.getContentAsString());
    }

    @Test
    void testRecuperaTrancaPorId() throws Exception {
        // Arrange
        when(trancaService.recuperaTrancaPorId(1)).thenReturn(t);
        String json = "{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"loc teste\",\"anoDeFabricacao\":\"ano teste\",\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}";

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
        String jsonResposta = "{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"teste\",\"anoDeFabricacao\":\"2020\",\"modelo\":\"teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(post("/tranca")
            .content(jsonEntrada)
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testCadastraNovaTrancaDadosIncorretos() throws Exception {
        // Arrange
        TrancaDTO dto = new TrancaDTO(1, "teste", "2020", "teste", "a");
        when(trancaService.cadastraTranca(dto)).thenThrow(IllegalArgumentException.class);

        String jsonEntrada = """
                {
                    "numero": 1,
                    "localizacao": "teste",
                    "anoDeFabricacao": "2020",
                    "modelo": "teste",
                    "status": "a"
                }
                """;

        // Act
        var response = this.mvc.perform(post("/tranca")
            .content(jsonEntrada)
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testAlteraDadosValidosTranca() throws Exception {
        // Arrange
        TrancaDTO dto = new TrancaDTO(1, "teste", "2020", "teste", "NOVA");
        Tranca t = new Tranca(dto);
        when(trancaService.alteraTranca(1, dto)).thenReturn(t);
        String jsonEntrada = """
                {
                    "numero": 1,
                    "localizacao": "teste",
                    "anoDeFabricacao": "2020",
                    "modelo": "teste",
                    "status": "NOVA"
                }
                """;
        String jsonResposta = "{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"teste\",\"anoDeFabricacao\":\"2020\",\"modelo\":\"teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(put("/tranca/{idTranca}", 1)
            .content(jsonEntrada)
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testAlteraDadosInvalidosTranca() throws Exception {
        // Arrange
        TrancaDTO dto = new TrancaDTO(1, "teste", "2020", "teste", "a");
        when(trancaService.alteraTranca(1, dto)).thenThrow(IllegalArgumentException.class);
        String jsonEntrada = """
                {
                    "numero": 1,
                    "localizacao": "teste",
                    "anoDeFabricacao": "2020",
                    "modelo": "teste",
                    "status": "a"
                }
                """;

        // Act
        var response = this.mvc.perform(put("/tranca/{idTranca}", 1)
            .content(jsonEntrada)
            .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testExcluiTranca() throws Exception {
        // Arrange
        doNothing().when(trancaService).excluiTranca(1);

        // Act
        var response = this.mvc.perform(delete("/tranca/{idTranca}", 1))
            .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testAlteraStatusTrancaCorreto() throws Exception {
        // Arrange
        when(trancaService.alteraStatusTranca(1, "NOVA")).thenReturn(t);

        String jsonResposta = "{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"loc teste\",\"anoDeFabricacao\":\"ano teste\",\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(post("/tranca/{idTranca}/status/{acao}", 1, "NOVA"))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testAlteraStatusTrancaInvalido() throws Exception {
        // Arrange
        when(trancaService.alteraStatusTranca(-2, "QUALQUER_COISA")).thenThrow(IllegalArgumentException.class);

        // Act
        var response = this.mvc.perform(post("/tranca/{idTranca}/status/{acao}", -2, "QUALQUER_COISA"))
                .andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testRealizaTrancamento() throws Exception {
        // Arrange
        when(trancaService.realizarTrancamento(1, 1)).thenReturn(t);

        String jsonResposta = "{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"loc teste\",\"anoDeFabricacao\":\"ano teste\",\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(post("/tranca/{idTranca}/trancar", 1)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testRealizaDestrancamento() throws Exception {
        // Arrange
        when(trancaService.realizarDestrancamento(1, 1)).thenReturn(t);

        String jsonResposta = "{\"id\":1,\"bicicleta\":0,\"numero\":1,\"localizacao\":\"loc teste\",\"anoDeFabricacao\":\"ano teste\",\"modelo\":\"modelo teste\",\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(post("/tranca/{idTranca}/destrancar", 1)
                .content("1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testIntegraNaRede() throws Exception {
        // Arrange
        InclusaoTrancaDTO inclusaoDTO = new InclusaoTrancaDTO(1, 1, 1);
        when(trancaService.integrarNaRede(inclusaoDTO)).thenReturn(true);
        String json = """
                {
                    "idTranca": 1,
                    "idFuncionario": 1,
                    "idTotem": 1
                }
                """;

        // Act
        var response = this.mvc.perform(post("/tranca/integrarNaRede")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        verify(trancaService).integrarNaRede(inclusaoDTO);
    }

    @Test
    void testRetiraDaRede() throws Exception {
        // Arrange
        RetiradaTrancaDTO retiradaDTO = new RetiradaTrancaDTO(1, 1, 1, "NOVA");
        when(trancaService.retirarDaRede(retiradaDTO)).thenReturn(true);
        String json = """
                {
                    "idTranca": 1,
                    "idFuncionario": 1,
                    "statusAcaoReparador": "NOVA",
                    "idTotem": 1
                }
                """;

        // Act
        var response = this.mvc.perform(post("/tranca/retirarDaRede")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        verify(trancaService).retirarDaRede(retiradaDTO);
    }

    @Test
    void testObtemBicicletaNaTranca() throws Exception {
        // Arrange
        Bicicleta bicicleta = new Bicicleta(new BicicletaDTO("marca teste1", "modelo teste1", "2020", 1, "NOVA"));
        when(trancaService.obterBicicletaNaTranca(1)).thenReturn(bicicleta);
        String jsonResposta = "{\"id\":1,\"marca\":\"marca teste1\",\"modelo\":\"modelo teste1\",\"ano\":\"2020\",\"numero\":1,\"status\":\"NOVA\"}";

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}/bicicleta", 1))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(jsonResposta, response.getContentAsString());
    }

    @Test
    void testObtemBicicletaNaTrancaIdInvalido() throws Exception {
        // Arrange
        when(trancaService.obterBicicletaNaTranca(-1)).thenThrow(IllegalArgumentException.class);

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}/bicicleta", -1))
                .andReturn().getResponse();

        // Assert
        assertEquals(422, response.getStatus());
        assertEquals(JSON_ERRO_422, response.getContentAsString());
    }

    @Test
    void testObtemBicicletaNaTrancaIdNaoExiste() throws Exception {
        // Arrange
        when(trancaService.obterBicicletaNaTranca(500)).thenThrow(NoSuchElementException.class);

        // Act
        var response = this.mvc.perform(get("/tranca/{idTranca}/bicicleta", 500))
                .andReturn().getResponse();

        // Assert
        assertEquals(404, response.getStatus());
        assertEquals(JSON_ERRO_404, response.getContentAsString());
    }
}
