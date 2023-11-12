package com.aluguel.service;

import com.aluguel.DTO.CiclistaDTO;
import com.aluguel.model.Ciclista;

import java.util.List;

import static com.aluguel.model.Ciclista.ciclistas;

public class CiclistaService {
    public Ciclista recuperaCiclistaPorId(int idCiclista) {
        if(!ciclistas.isEmpty()) {
            for (Ciclista ciclista : ciclistas) {
                if (ciclista.getId() == idCiclista) {
                    return ciclista;
                }
            }
        }
        throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não existe");
    }

    public List<Ciclista> recuperaCiclistas() { return ciclistas; }

    public Ciclista cadastraCiclista(CiclistaDTO dadosCadastroCiclista) {
        Ciclista ciclista = new Ciclista(dadosCadastroCiclista);
        ciclistas.add(ciclista);
        return ciclista;
    }

    public Ciclista alteraCiclista(int idCiclista, CiclistaDTO dadosAlteracaoCiclista) {
        if(!ciclistas.isEmpty()) {
            for (Ciclista ciclista : ciclistas) {
                if (ciclista.getId() == idCiclista) {
                    ciclista.atualizaCiclista(dadosAlteracaoCiclista);
                    return ciclista;
                }
            }
        }
        throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não existe");
    }

}

