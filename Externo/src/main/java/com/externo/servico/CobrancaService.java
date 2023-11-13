package com.externo.servico;

import com.externo.DTO.CobrancaDTO;
import com.externo.DTO.EmailDTO;
import com.externo.model.Cobranca;
import org.springframework.stereotype.Service;
import java.lang.Math;

import static com.externo.model.Cobranca.cobrancas;

import java.util.List;

@Service
public class CobrancaService {

    public boolean realizaCobranca(CobrancaDTO dadosCobranca) {
        //50% de chance de pagamento ser aprovado
        if (Math.random() < 0.5) {
            return true;
        }
        return false;
    }

    public boolean processaCobrancasEmFila() {
        for (Cobranca c : cobrancas) {
            if(c.status.equals("PENDENTE")) {
                if(realizaCobranca(new CobrancaDTO(c.status, c.horaSolicitacao, c.horaFinalizacao, c.valor, c.ciclista))) {
                    c.status = "PAGA";
                }
                else {
                    c.status = "FALHA";
                }
            }
        }
        return true;
    }

    public boolean incluiFilaCobranca(CobrancaDTO dadosCobranca) {
        Cobranca cob = new Cobranca(dadosCobranca);
        cob.status = "PENDENTE";

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
