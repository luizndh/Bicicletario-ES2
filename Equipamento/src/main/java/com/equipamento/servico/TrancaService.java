package com.equipamento.servico;

import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.StatusTranca;
import com.equipamento.model.Tranca;
import jdk.jshell.Snippet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrancaService {

    public Tranca recuperaTrancaPorId(int idTranca) {
        for(Tranca t: Tranca.trancas) {
            if(t.getId() == idTranca) {
                return t;
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }

    public List<Tranca> recuperaTrancas() {
        return Tranca.trancas;
    }

    //TODO: validar os dados
    public Tranca cadastraTranca(TrancaDTO dadosTranca) {
        Tranca t = new Tranca(dadosTranca);
        Tranca.trancas.add(t);
        return t;
    }

    //TODO: validar os dados
    public Tranca alteraTranca(int idTranca, TrancaDTO dadosAlteracaoTranca) {
        for(Tranca t: Tranca.trancas) {
            if(t.getId() == idTranca) {
                t.atualizaTranca(dadosAlteracaoTranca);
                return t;
            }
        }
        throw new IllegalArgumentException("A tranca com id " + idTranca + " não existe");
    }

}
