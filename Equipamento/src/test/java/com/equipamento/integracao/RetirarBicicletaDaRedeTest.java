package com.equipamento.integracao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.equipamento.dto.RetiradaBicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.servico.BicicletaService;
import com.equipamento.servico.IntegracaoService;
import static com.equipamento.util.Constantes.URL_ALUGUEL;

public class RetirarBicicletaDaRedeTest {
    private BicicletaService bicicletaService = new BicicletaService();
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
    void testRetiraBicicletaDaRedeEmReparo() {
        //Arrange
        RetiradaBicicletaDTO dadosRetirada = new RetiradaBicicletaDTO( 3,4 ,12345, "EM_REPARO");
        Bicicleta b = bicicletaService.recuperaBicicletaPorId(4);

        //Act
        boolean emailEnviado = bicicletaService.retirarDaRede(dadosRetirada);

        //Assert
        assertEquals(b.getStatus(), Bicicleta.StatusBicicleta.EM_REPARO);
        assertTrue(emailEnviado);
    }

    @Test
    void testRetiraBicicletaDaRedeAposentada() {
        //Arrange
        RetiradaBicicletaDTO dadosRetirada = new RetiradaBicicletaDTO( 3,4 ,12345, "APOSENTADA");
        Bicicleta b = bicicletaService.recuperaBicicletaPorId(4);

        //Act
        boolean emailEnviado = bicicletaService.retirarDaRede(dadosRetirada);

        //Assert
        assertEquals(b.getStatus(), Bicicleta.StatusBicicleta.APOSENTADA);
        assertTrue(emailEnviado);
    }

    @Test
    void testRetiraBicicletaFuncionarioNaoExiste() {
        //Arrange
        RetiradaBicicletaDTO dadosRetirada = new RetiradaBicicletaDTO( 3,4 ,100, "APOSENTADA");


        //Act + Assert
        assertThrows(IllegalArgumentException.class, () -> bicicletaService.retirarDaRede(dadosRetirada));
    }
}
