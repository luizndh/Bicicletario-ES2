package com.example.equipamento.service;

import com.example.equipamento.model.Tranca;
import org.springframework.stereotype.Service;

@Service
public class TrancaService {

    public Tranca getTrancaPorId(int idTranca) {
        for(Tranca t: Tranca.trancas) {
            if(t.getId() == idTranca) {
                return t;
            }
        }
        return null;
    }


}
