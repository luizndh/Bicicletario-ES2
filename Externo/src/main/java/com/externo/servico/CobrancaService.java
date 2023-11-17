package com.externo.servico;

import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import org.springframework.stereotype.Service;
import java.lang.Math;
import com.stripe.Stripe;

import static com.externo.model.Cobranca.cobrancas;

@Service
public class CobrancaService {

    public boolean realizaCobranca(CobrancaDTO dadosCobranca) {
        Stripe.apiKey = "pk_test_51ODEoGK2SlPC0gAXqlT8OczBbK8MmnoQHihzsC1N1LgijjcU6Cn4L24yJgdtOYXZyBjv0MElx1EDf5R5nRdGvRom00DSGqfPfn";

        //50% de chance de pagamento ser aprovado
        if(!dadosCobranca.status().equals(Cobranca.StatusCobranca.PENDENTE)) return false;
        if (Math.random() < 0.5) return true;
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
