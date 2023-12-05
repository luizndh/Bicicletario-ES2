package com.equipamento.integracao;

import com.equipamento.controller.TrancaController;
import com.equipamento.dto.RetiradaTrancaDTO;
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

import static com.equipamento.util.Constantes.URL_ALUGUEL;
import static org.junit.jupiter.api.Assertions.*;

public class RetirarTrancaDaRedeTest {
    private TrancaService trancaService = new TrancaService();
    private IntegracaoService integracaoService = new IntegracaoService();

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
    void testRetiraTrancaDaRedeEmReparo() {
        //Arrange
        RetiradaTrancaDTO dadosRetirada = new RetiradaTrancaDTO(1, 6, 1, "EM_REPARO");
        Tranca t = trancaService.recuperaTrancaPorId(6);

        //Act
        boolean emailEnviado = trancaService.retirarDaRede(dadosRetirada);

        //Assert
        assertEquals(Tranca.StatusTranca.EM_REPARO, t.getStatus());
        assertTrue(emailEnviado);
    }

    @Test
    void testRetiraTrancaDaRedeAposentada() {
        //Arrange
        RetiradaTrancaDTO dadosRetirada = new RetiradaTrancaDTO(1, 2, 1, "APOSENTADA");
        Tranca t = trancaService.recuperaTrancaPorId(2);

        //Act
        boolean emailEnviado = trancaService.retirarDaRede(dadosRetirada);

        //Assert
        assertEquals(Tranca.StatusTranca.APOSENTADA, t.getStatus());
        assertTrue(emailEnviado);
    }

    @Test
    void testRetiraTrancaDeRedeFuncionarioNaoExiste() {
        //Arrange
        RetiradaTrancaDTO dadosRetirada = new RetiradaTrancaDTO(1, 2, 100, "APOSENTADA");
        Tranca t = trancaService.recuperaTrancaPorId(2);

        //Act
        boolean emailEnviado = trancaService.retirarDaRede(dadosRetirada);

        //Assert
        assertEquals(Tranca.StatusTranca.APOSENTADA, t.getStatus());
        assertFalse(emailEnviado);
    }
}
