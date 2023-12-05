package com.aluguel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.dto.CobrancaDTO;
import com.aluguel.dto.EmailDTO;
import com.aluguel.dto.NovaCobrancaDTO;
import com.aluguel.dto.TrancaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Integracoes {
    public boolean enviaEmail(String email, String assunto, String mensagem) {
        ObjectMapper mapper = new ObjectMapper();
        EmailDTO novoEmail = new EmailDTO(email, assunto, mensagem);

        try {
            String jsonEntrada = mapper.writeValueAsString(novoEmail);

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/enviarEmail"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEntrada))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TrancaDTO recuperaTrancaPorId(int idTranca) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/tranca/" + idTranca))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            TrancaDTO trancaResponse = mapper.readValue(response.body(), TrancaDTO.class);
            return trancaResponse;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BicicletaDTO recuperaBicicletaPorId(int idBicicleta) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/bicicleta/" + idBicicleta))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            BicicletaDTO bicicletaResponse = mapper.readValue(response.body(), BicicletaDTO.class);
            return bicicletaResponse;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CobrancaDTO realizaCobranca(NovaCobrancaDTO novaCobranca) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/cobranca"))
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(novaCobranca)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            CobrancaDTO cobrancaResponse = mapper.readValue(response.body(), CobrancaDTO.class);
            return cobrancaResponse;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean trancaTranca(int idTranca, int idBicicleta) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/tranca/" + idTranca + "/trancar"))
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(idBicicleta)))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean destrancaTranca(int idTranca, int idBicicleta) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/tranca/" + idTranca + "/destrancar"))
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(idBicicleta)))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validaCartaoDeCredito(CartaoDeCreditoDTO cartaoDeCredito) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/validaCartaoDeCredito"))
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(cartaoDeCredito)))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BicicletaDTO recuperaBicicletaAlugada (int idBicicleta) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/bicicleta/" + idBicicleta + "/aluguel"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            BicicletaDTO bicicletaResponse = mapper.readValue(response.body(), BicicletaDTO.class);
            return bicicletaResponse;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean integrarBicicletaNaRede(int idBicicleta) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/bicicleta/integrarNaRede"))
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(idBicicleta)))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

            return true;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
