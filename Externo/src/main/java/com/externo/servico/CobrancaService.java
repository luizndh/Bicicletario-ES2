package com.externo.servico;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;

import static com.externo.model.Cobranca.cobrancas;

@Service
public class CobrancaService {

    public boolean realizaCobranca(CobrancaDTO dadosCobranca) {
        if (!dadosCobranca.status().equals(Cobranca.StatusCobranca.PENDENTE.toString())) return false;

        try {
            Stripe.apiKey = "sk_test_51ODEoGK2SlPC0gAXe7gRKx3tgwYgdxaYf8xoTkJvrMdUXMSXMPzwmdFEprKG654eo1h8JRuyQtNvqIU8iPW7T7nE00W6te3PX4";

            //TODO INTEGRACAO
            //vou criar um cartao de credito para simular,
            //mas quando integrar ele vai pegar do cartao do ciclista usando o id
            CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO("João da Silva", "1212121212121212", "09/2029", "123");
            //IMPORTANTE: por causa de uma limitacao do stripe, por enquanto ele nao consegue passar o cartao diretamente

            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder().setAmount(dadosCobranca.valor()).setCurrency("brl").build();
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            PaymentIntent confirmedPaymentIntent = paymentIntent.confirm(PaymentIntentConfirmParams.builder().setPaymentMethod("pm_card_visa").build());

            if (confirmedPaymentIntent.getStatus().equals("succeeded")) {
                System.out.println("Cobranca realizada com sucesso");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Erro no processamento do pagamento");
        }
        return false;
    }

    public boolean processaCobrancasEmFila() {
        for (Cobranca c : cobrancas) {
            if (c.getStatus().equals(Cobranca.StatusCobranca.PENDENTE)) {
                if (realizaCobranca(new CobrancaDTO(c.getStatus().toString(), c.getHoraSolicitacao(), c.getHoraFinalizacao(), c.getValor(), c.getCiclista()))) {
                    c.setStatus(Cobranca.StatusCobranca.PAGA);
                } else {
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
        if (!cobrancas.isEmpty()) {
            for (Cobranca c : cobrancas) {
                if (c.getId() == idCobranca) {
                    return c;
                }
            }
        }
        throw new IllegalArgumentException("A cobranca com id " + idCobranca + " não existe");
    }
}
