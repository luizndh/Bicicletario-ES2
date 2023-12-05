package com.equipamento.integracao;

import com.equipamento.controller.TrancaController;
import com.equipamento.dto.InclusaoTrancaDTO;
import com.equipamento.model.Tranca;
import com.equipamento.servico.IntegracaoService;
import com.equipamento.servico.TrancaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

import static com.equipamento.util.Constantes.URL_ALUGUEL;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrarTrancaNaRedeTest {
    private TrancaService trancaService = new TrancaService();
    private IntegracaoService integracaoService = new IntegracaoService();
    private TrancaController trancaController = new TrancaController();

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
    void testIntegraTrancaNaRedeCorreta() {
        //Arrange
        InclusaoTrancaDTO dto = new InclusaoTrancaDTO(1, 5, 12345);
        Tranca t = trancaService.recuperaTrancaPorId(5);

        //Act
        boolean emailEnviado = trancaService.integrarNaRede(dto);

        //Assert
        assertEquals(t.getStatus(), Tranca.StatusTranca.LIVRE);
        assertTrue(emailEnviado);
    }

    @Test
    void testIntegraNaRedeFuncionarioNaoExiste() {
        //Arrange
        InclusaoTrancaDTO dto = new InclusaoTrancaDTO(1, 5, 100);

        // Act + assert
        assertThrows(IllegalArgumentException.class, () -> trancaService.integrarNaRede(dto));
    }
}
