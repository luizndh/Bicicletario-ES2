package com.aluguel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.service.AluguelService;

@SpringBootTest
@AutoConfigureMockMvc
public class DevolucaoControllerTest {

    @MockBean
    private AluguelService aluguelService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testRegistraDevolucao() throws Exception {
        // Arrange
        String idBicicleta = "1";
        when(aluguelService.recuperaAluguelPorIdBicicleta(Integer.parseInt(idBicicleta))).then(invocation -> {
            Aluguel aluguel = new Aluguel(new NovoAluguelDTO("1", "2"));
            aluguel.atualizaAluguel(new AluguelDTO(1, "10:00", 2, "11:00", 10, 3, 4));
            return aluguel;
        });
        //String horaFim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String jsonEntrada = """
                {
                    "idBicicleta": "1",
                    "trancaFim": "1"
                }
                """;
        //String json = "{\"bicicleta\":1,\"horaInicio\":\"2021-10-10 10:10:10\",\"trancaFim\":1,\"horaFim\":\""+horaFim+"\",\"cobranca\":10,\"ciclista\":1}";

        // Act
        var response = this.mvc.perform(post("/devolucao").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        //assertEquals(json, response.getContentAsString());
    }
}