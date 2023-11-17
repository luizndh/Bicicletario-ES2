package com.externo.servico;

import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
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
        if(!dadosCobranca.status().equals(Cobranca.StatusCobranca.PENDENTE)) return false;

        try {
            Stripe.apiKey = "sk_test_51ODEoGK2SlPC0gAXe7gRKx3tgwYgdxaYf8xoTkJvrMdUXMSXMPzwmdFEprKG654eo1h8JRuyQtNvqIU8iPW7T7nE00W6te3PX4";

            Map<String, Object> params = new HashMap<>();
            params.put("amount", dadosCobranca.valor());
            params.put("currency", "brl");
            params.put("payment_method","card");

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            System.out.println(paymentIntent.getStatus());
            System.out.println(paymentIntent.getAmount());

            if(paymentIntent.getStatus().equals("succeeded")) {
                return true;
            }
        } catch (StripeException e) {
            e.printStackTrace();
            //TODO handling esse erro do stripe
        }
        //sends request to Stripe API, and returns true if payment is approved.
        //(this is only a simulation, no real payment is made)

        //50% de chance de pagamento ser aprovado
        /*
        if (Math.random() < 0.5) return true;
        return false;
         */
        return false;
    }

    public boolean processaCobrancasEmFila() {
        for (Cobranca c : cobrancas) {
            if(c.getStatus().equals(Cobranca.StatusCobranca.PENDENTE)) {
                if(realizaCobranca(new CobrancaDTO(c.getStatus(), c.getHoraSolicitacao(), c.getHoraFinalizacao(), c.getValor(), c.getCiclista()))) {
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
