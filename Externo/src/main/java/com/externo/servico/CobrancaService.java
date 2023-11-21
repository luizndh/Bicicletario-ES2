package com.externo.servico;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;

import java.util.ArrayList;
import java.util.List;

import static com.externo.model.Cobranca.cobrancas;

@Service
public class CobrancaService {

    public Cobranca realizaCobranca(Cobranca dadosCobranca) {
        System.out.println("Realizando cobranca com id " + dadosCobranca.getId());
        System.out.println("sussy: " + dadosCobranca.getStatus().toString());
        if (!dadosCobranca.getStatus().toString().equals(Cobranca.StatusCobranca.PENDENTE.toString())) throw new IllegalArgumentException("Pagamento nao esta pendente!");

        try {
            Stripe.apiKey = "sk_test_51ODEoGK2SlPC0gAXe7gRKx3tgwYgdxaYf8xoTkJvrMdUXMSXMPzwmdFEprKG654eo1h8JRuyQtNvqIU8iPW7T7nE00W6te3PX4";

            //TODO INTEGRACAO
            //vou criar um cartao de credito para simular,
            //mas quando integrar ele vai pegar do cartao do ciclista usando o id
            CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO("João da Silva", "1212121212121212", "09/2029", "123");
            //IMPORTANTE: por causa de uma limitacao do stripe, por enquanto ele nao consegue passar o cartao diretamente

            PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder().setAmount(dadosCobranca.getValor()).setCurrency("brl").build();
            PaymentIntent paymentIntent = PaymentIntent.create(createParams);

            PaymentIntent confirmedPaymentIntent = paymentIntent.confirm(PaymentIntentConfirmParams.builder().setPaymentMethod("pm_card_visa").build());

            if (confirmedPaymentIntent.getStatus().equals("succeeded")) {
                System.out.println("Cobranca realizada com sucesso");
                //altera o status da cobranca na fila para paga
                for (Cobranca c : cobrancas) {
                    if (c.getId() == dadosCobranca.getId()) {
                        c.setStatus(Cobranca.StatusCobranca.PAGA);
                    }
                }
                return dadosCobranca;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Erro no processamento do pagamento");
        }
        throw new IllegalArgumentException("Erro no processamento do pagamento");
    }

    public List<Cobranca> processaCobrancasEmFila() {
        List<Cobranca> processadas = new ArrayList<>();
        boolean flag = false;
        for (Cobranca c : cobrancas) {
            if (c.getStatus().equals(Cobranca.StatusCobranca.PENDENTE)) {
                flag = true;
                System.out.println("Processando cobranca com id " + c.getId());
                processadas.add(realizaCobranca(new Cobranca(new CobrancaDTO(c.getStatus().toString(), c.getHoraSolicitacao(), c.getHoraFinalizacao(), c.getValor(), c.getCiclista()))));
            }
        }
        if(!flag) throw new IllegalArgumentException("Nao ha cobrancas pendentes");
        return processadas;
    }

    public Cobranca incluiFilaCobranca(CobrancaDTO dadosCobranca) {
        if(dadosCobranca.valor() < 0) throw new IllegalArgumentException("Valor da cobranca nao pode ser negativo");
        if(dadosCobranca.ciclista() < 0) throw new IllegalArgumentException("Id do ciclista nao pode ser negativo");

        Cobranca cob = new Cobranca(dadosCobranca);
        //cob.setStatus(Cobranca.StatusCobranca.PENDENTE);

        cobrancas.add(cob);
        return cob;
    }

    public Cobranca obterCobranca(int idCobranca) {
        System.out.println("Obtendo cobranca com id " + idCobranca);
        if (!cobrancas.isEmpty()) {
            for (Cobranca c : cobrancas) {
                if (c.getId() == idCobranca) {
                    System.out.println("Valor cobranca encontrada: " + c.getValor());
                    return c;
                }
            }
        }
        throw new IllegalArgumentException("A cobranca com id " + idCobranca + " não existe");
    }
}
