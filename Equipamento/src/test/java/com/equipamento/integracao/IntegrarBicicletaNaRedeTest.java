package com.equipamento.integracao;

import com.equipamento.controller.BicicletaController;
import com.equipamento.dto.InclusaoBicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.servico.BicicletaService;
import com.equipamento.servico.IntegracaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static com.equipamento.util.Constantes.URL_ALUGUEL;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrarBicicletaNaRedeTest {
    private final BicicletaService bicicletaService = new BicicletaService();
    private final IntegracaoService integracaoService = new IntegracaoService();

    @BeforeEach
    void setUp() throws URISyntaxException, IOException, InterruptedException {
        integracaoService.restaurarDados();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URL_ALUGUEL + "/restaurarDados"))
                .GET()
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void testIntegraNaRedeCorreta() {
        //Arrange
        InclusaoBicicletaDTO dto = new InclusaoBicicletaDTO(2, 4, 1);
        Bicicleta b = bicicletaService.recuperaBicicletaPorId(4);

        //Act
        boolean emailEnviado = bicicletaService.integrarNaRede(dto);

        //Assert
        assertEquals(b.getStatus(), Bicicleta.StatusBicicleta.DISPONIVEL);
        assertTrue(emailEnviado);
    }

    @Test
    void testIntegraNaRedeFuncionarioNaoExiste() {
        //Arrange
        InclusaoBicicletaDTO dto = new InclusaoBicicletaDTO(2, 4, 10);

        //Act
        boolean emailEnviado = bicicletaService.integrarNaRede(dto);

        //Assert
        assertFalse(emailEnviado);
    }


    @Test
    void testIntegraNaRedeBicicletaEmUso() {
        //Arrange
        InclusaoBicicletaDTO dto = new InclusaoBicicletaDTO(2, 5, 1);
        Bicicleta b = bicicletaService.recuperaBicicletaPorId(5);

        //Act
        boolean emailEnviado = bicicletaService.integrarNaRede(dto);

        //Assert
        assertEquals(b.getStatus(), Bicicleta.StatusBicicleta.EM_USO);
        assertTrue(emailEnviado);
    }
}
