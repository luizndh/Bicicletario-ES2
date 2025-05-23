package com.externo.servico;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.externo.dto.CartaoDeCreditoResponseDTO;
import com.externo.dto.CiclistaResponseDTO;
import com.externo.dto.CobrancaDTO;
import com.externo.dto.EmailDTO;
import com.externo.model.Cobranca;
import static com.externo.model.Cobranca.cobrancas;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class CobrancaService {

    @Autowired
    private EmailService service;

    //final String URL = "http://ec2-54-235-47-98.compute-1.amazonaws.com:8082";
    final String URL = "http://localhost:8082";

    public Cobranca realizaCobranca(Cobranca dadosCobranca) {
        if(dadosCobranca.getStatus() != null) {
            if (!dadosCobranca.getStatus().equals(Cobranca.StatusCobranca.PENDENTE.toString()))
                throw new IllegalArgumentException("Pagamento nao esta pendente!");
            if (dadosCobranca.getValor() < 0)
                throw new IllegalArgumentException("Valor da cobranca deve ser maior que 0");
            if (dadosCobranca.getCiclista() < 0)
                throw new IllegalArgumentException("Id do ciclista nao pode ser negativo");
        }
        else if(dadosCobranca.getHoraSolicitacao() == null) {
            if (dadosCobranca.getValor() < 0)
                throw new IllegalArgumentException("Valor da cobranca deve ser maior que 0");
            if(dadosCobranca.getCiclista() < 0)
                throw new IllegalArgumentException("Id do ciclista nao pode ser negativo");
            dadosCobranca.setHoraSolicitacao(LocalDateTime. now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        CiclistaResponseDTO ciclistaResponse = null;
        String emailCiclista = null;
        CartaoDeCreditoResponseDTO cartaoCiclista = null;
        try {
            ciclistaResponse = recuperaCiclistaPorId(dadosCobranca.getCiclista());
            emailCiclista = ciclistaResponse.email();
            //String emailCiclista = "lucas.arruda@edu.unirio.br";

            cartaoCiclista = recuperaCartaoDeCreditoDeCiclistaPorId(dadosCobranca.getCiclista());
            //CartaoDeCreditoResponseDTO cartaoCiclista = new CartaoDeCreditoResponseDTO(1, "joao", "4242424242424242", "12/2029", "123");
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        if(emailCiclista != null) {
            try {
                Stripe.apiKey = "KEY_STRIPE";

                PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder().setAmount(dadosCobranca.getValor() * 100).setCurrency("brl").build();
                PaymentIntent paymentIntent = PaymentIntent.create(createParams);

                PaymentIntent confirmedPaymentIntent = paymentIntent.confirm(PaymentIntentConfirmParams.builder().setPaymentMethod("pm_card_visa").build());

                if (confirmedPaymentIntent.getStatus().equals("succeeded")) {
                    //envia email notificando ciclista que a cobranca atrasada foi paga
                    service.enviarEmail(new EmailDTO(emailCiclista, "Cobranca paga", "Sua cobranca em atraso com o valor " + dadosCobranca.getValor() + " foi paga com sucesso!"));

                    //altera o status da cobranca na fila para paga
                    for (Cobranca c : cobrancas) {
                        if (c.getId() == dadosCobranca.getId()) {
                            c.setStatus(Cobranca.StatusCobranca.PAGA.toString());
                            c.setHoraFinalizacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                    }
                    dadosCobranca.setStatus(Cobranca.StatusCobranca.PAGA.toString());
                    dadosCobranca.setHoraFinalizacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    return dadosCobranca;
                }

            } catch (Exception e) {
                e.printStackTrace();

                service.enviarEmail(new EmailDTO(emailCiclista, "Erro na cobranca em atraso", "Houve um erro no processamento do pagamento da sua cobranca em atraso com o valor " + dadosCobranca.getValor()));
                throw new IllegalArgumentException("Erro no processamento do pagamento");
            }
        }

        if(emailCiclista != null)
            service.enviarEmail(new EmailDTO(emailCiclista, "Erro na cobranca em atraso", "Houve um erro no pagamento da sua cobranca em atraso com o valor " + dadosCobranca.getValor()));
        throw new IllegalArgumentException("Erro no processamento do pagamento");
    }

    public List<Cobranca> processaCobrancasEmFila() {
        List<Cobranca> processadas = new ArrayList<>();
        boolean flag = false;
        for (Cobranca c : cobrancas) {
            if (c.getStatus().equals(Cobranca.StatusCobranca.PENDENTE.toString())) {
                flag = true;
                if(c.getId() < 0) throw new IllegalArgumentException("Id da cobranca deve ser positivo");
                processadas.add(realizaCobranca(c));
            }
        }
        if(!flag) throw new IllegalArgumentException("Nao ha cobrancas pendentes");
        return processadas;
    }

    public Cobranca incluiFilaCobranca(CobrancaDTO dadosCobranca) {
        if(dadosCobranca.valor() < 0) throw new IllegalArgumentException("Valor da cobranca nao pode ser negativo");
        if(dadosCobranca.ciclista() < 0) throw new IllegalArgumentException("Id do ciclista nao pode ser negativo");

        Cobranca cob = new Cobranca(dadosCobranca);
        cob.setStatus(Cobranca.StatusCobranca.PENDENTE.toString());
        cob.setHoraSolicitacao(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        cobrancas.add(cob);
        return cob;
    }

    public Cobranca obterCobranca(int idCobranca) {
        if (!cobrancas.isEmpty()) {
            for (Cobranca c : cobrancas) {
                if (c.getId() == idCobranca) {
                    return c;
                }
            }
        }
        throw new NoSuchElementException("A cobranca com id " + idCobranca + " não existe");
    }

    private CiclistaResponseDTO recuperaCiclistaPorId(int idCiclista) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + "/ciclista/" + idCiclista))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 404) throw new NoSuchElementException("Ciclista com id " + idCiclista + " nao existe");
            if(response.statusCode() == 422) throw new IllegalArgumentException("Argumento invalido");

            CiclistaResponseDTO ciclistaResponse = mapper.readValue(response.body(), CiclistaResponseDTO.class);
            return ciclistaResponse;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private CartaoDeCreditoResponseDTO recuperaCartaoDeCreditoDeCiclistaPorId(int idCiclista) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + "/cartaoDeCredito/" + idCiclista))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 404) throw new NoSuchElementException("Ciclista com id " + idCiclista + " nao existe");
            if(response.statusCode() == 422) throw new IllegalArgumentException("Argumento invalido");

            CartaoDeCreditoResponseDTO cartaoResponse = mapper.readValue(response.body(), CartaoDeCreditoResponseDTO.class);
            return cartaoResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
