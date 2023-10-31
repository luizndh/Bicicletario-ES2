package com.equipamento.servico;

import com.equipamento.DTO.BicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Tranca;

import java.util.List;

import static com.equipamento.model.Bicicleta.bicicletas;
import static com.equipamento.model.Tranca.trancas;

public class BicicletaService {
    public Bicicleta recuperaBicicletaPorId(int idBicicleta) {
        if(!bicicletas.isEmpty()) {
            for (Bicicleta b : bicicletas) {
                if (b.getId() == idBicicleta) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("A bicicleta com id " + idBicicleta + " não existe");
    }

    public List<Bicicleta> recuperaBicicletas() { return bicicletas; }

    public Bicicleta cadastraBicicleta(BicicletaDTO dadosCadastroBicicleta) {
        Bicicleta b = new Bicicleta(dadosCadastroBicicleta);
        bicicletas.add(b);
        return b;
    }

    public Bicicleta alteraBicicleta(int idBicicleta, BicicletaDTO dadosAlteracaoBicicleta) {
        if(!bicicletas.isEmpty()) {
            for (Bicicleta b : bicicletas) {
                if (b.getId() == idBicicleta) {
                    b.atualizaBicicleta(dadosAlteracaoBicicleta);
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("A bicicleta com id " + idBicicleta + " não existe");
    }

    public void excluiBicicleta(int idBicicleta) {
        if(!bicicletas.isEmpty()) {
            for (Bicicleta b : bicicletas) {
                if (b.getId() == idBicicleta) {
                    bicicletas.remove(b);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("A bicicleta com id " + idBicicleta + " não existe");
    }
}
