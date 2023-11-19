package com.externo.servico;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import java.lang.Math;
import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;

import static com.externo.model.Cobranca.cobrancas;

@Service
public class CobrancaService {

    public boolean realizaCobranca(CobrancaDTO dadosCobranca) {
        if(!dadosCobranca.status().equals(Cobranca.StatusCobranca.PENDENTE.toString())) return false;

        //STRIPE
        try {
            Stripe.apiKey = "sk_test_51ODEoGK2SlPC0gAXe7gRKx3tgwYgdxaYf8xoTkJvrMdUXMSXMPzwmdFEprKG654eo1h8JRuyQtNvqIU8iPW7T7nE00W6te3PX4";

            //TODO INTEGRACAO
            //vou criar um cartao de credito para simular,
            //mas quando integrar ele vai pegar do cartao do ciclista usando o id
            CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO("João da Silva", "1212121212121212", "09/2029", "123");

            // Create a test token
            TokenCreateParams.Card card = TokenCreateParams.Card.builder().setNumber(cartao.numero()).setExpMonth(cartao.validade().substring(0, 2)).setExpYear(cartao.validade().substring(3)).setCvc(cartao.cvv()).build();

            TokenCreateParams tokenParams = TokenCreateParams.builder().setCard(card).build();

            Token token = Token.create(tokenParams);

            // Create a PaymentMethod using the token
            Map<String, Object> paymentMethodParams = new HashMap<>();
            paymentMethodParams.put("type", "card");
            paymentMethodParams.put("card[token]", token.getId());

            PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

            // Create PaymentIntent using the PaymentMethod ID
            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder().setAmount((long) dadosCobranca.valor()).setCurrency("brl").setPaymentMethod(paymentMethod.getId()).build();

            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            // Display information
            System.out.println("Token ID: " + token.getId());
            System.out.println("PaymentMethod ID: " + paymentMethod.getId());
            System.out.println("PaymentIntent Status: " + paymentIntent.getStatus());
            System.out.println("PaymentIntent Amount: " + paymentIntent.getAmount());

            if (paymentIntent.getStatus().equals("succeeded")) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("Erro no stripe!");
            e.printStackTrace();
            //TODO handling esse erro do stripe
        }

        return false;
    }

    public boolean processaCobrancasEmFila() {
        for (Cobranca c : cobrancas) {
            if(c.getStatus().equals(Cobranca.StatusCobranca.PENDENTE)) {
                if(realizaCobranca(new CobrancaDTO(c.getStatus().toString(), c.getHoraSolicitacao(), c.getHoraFinalizacao(), c.getValor(), c.getCiclista()))) {
                    c.setStatus(Cobranca.StatusCobranca.PAGA);
                }
                else {
                    c.setStatus(Cobranca.StatusCobranca.FALHA);
                }
            }
        }
        return true;
    }

    public boolean incluiFilaCobranca(CobrancaDTO dadosCobranca) {
        Cobranca cob = new Cobranca(dadosCobranca);
        cob.setStatus(Cobranca.StatusCobranca.PENDENTE);

        cobrancas.add(cob);
        return true;
    }

    public Cobranca obterCobranca(int idCobranca) {
        if(!cobrancas.isEmpty()) {
            for (Cobranca c : cobrancas) {
                if (c.getId() == idCobranca) {
                    return c;
                }
            }
        }
        throw new IllegalArgumentException("A cobranca com id " + idCobranca + " não existe");
    }
}
