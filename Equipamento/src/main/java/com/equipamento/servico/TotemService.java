package com.equipamento.servico;

import com.equipamento.DTO.TotemDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Totem;
import com.equipamento.model.Tranca;

import java.util.List;

import static com.equipamento.model.Totem.totens;

public class TotemService {
    public void excluiTotem(int idTotem) {
        if(!totens.isEmpty()) {
            for (Totem t : totens) {
                if (t.getId() == idTotem) {
                    totens.remove(t);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("O totem com id " + idTotem + " não existe");
    }

    public Totem alteraTotem(int idTotem, TotemDTO dadosAlteracaoTotem) {
        if(!totens.isEmpty()) {
            for (Totem t : totens) {
                if (t.getId() == idTotem) {
                    t.atualizaTotem(dadosAlteracaoTotem);
                    return t;
                }
            }
        }
        throw new IllegalArgumentException("O totem com id " + idTotem + " não existe");
    }

    public Totem cadastraTotem(TotemDTO dadosCadastroTotem) {
        Totem t = new Totem(dadosCadastroTotem);
        totens.add(t);
        return t;
    }

    public List<Totem> recuperaTotens() {
        return totens;
    }

    public List<Tranca> recuperaTrancasDoTotem(int idTotem) {
        if(!totens.isEmpty()) {
            for (Totem t : totens) {
                if (t.getId() == idTotem) {
                    return t.getTrancas();
                }
            }
        }
        throw new IllegalArgumentException("O totem com id " + idTotem + " não existe");
    }

    public List<Bicicleta> recuperaBicicletasDoTotem(int idTotem) {
        if(!totens.isEmpty()) {
            for (Totem t : totens) {
                if (t.getId() == idTotem) {
                    return t.getBicicletas();
                }
            }
        }
        throw new IllegalArgumentException("O totem com id " + idTotem + " não existe");
    }
}
