package com.equipamento.servico;

import static com.equipamento.model.Tranca.trancas;

import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.Tranca;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrancaService {

    public Tranca recuperaTrancaPorId(int idTranca) {
        if(!trancas.isEmpty()) {
            for (Tranca t : trancas) {
                if (t.getId() == idTranca) {
                    return t;
                }
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }

    public List<Tranca> recuperaTrancas() {
        return trancas;
    }

    //TODO: validar os dados
    public Tranca cadastraTranca(TrancaDTO dadosCadastroTranca) {
        Tranca t = new Tranca(dadosCadastroTranca);
        trancas.add(t);
        return t;
    }

    //TODO: validar os dados
    public Tranca alteraTranca(int idTranca, TrancaDTO dadosAlteracaoTranca) {
        if(!trancas.isEmpty()) {
            for (Tranca t : trancas) {
                if (t.getId() == idTranca) {
                    t.atualizaTranca(dadosAlteracaoTranca);
                    return t;
                }
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }

    //TODO: validar os dados
    public void excluiTranca(int idTranca) {
        if(!trancas.isEmpty()) {
            for (Tranca t : trancas) {
                if (t.getId() == idTranca) {
                    trancas.remove(t);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }
}
