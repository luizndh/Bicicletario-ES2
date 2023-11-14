package com.equipamento.servico;

import com.equipamento.DTO.InclusaoBicicletaDTO;
import com.equipamento.DTO.RetiradaBicicletaDTO;
import com.equipamento.DTO.TotemDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Totem;
import com.equipamento.model.Tranca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.equipamento.model.Totem.totens;

@Service
public class TotemService {


    @Autowired
    private BicicletaService bicicletaService;

    @Autowired TrancaService trancaService;

    private Totem recuperaTotemPorId(int idTotem) {
        if(idTotem < 0) {
            throw new IllegalArgumentException("Id da tranca inválido");
        }
        for (Totem t : totens) {
            if (t.getId() == idTotem) {
                return t;
            }
        }
        throw new NoSuchElementException("O totem com id " + idTotem + " não existe");
    }

    /*

    public void excluiTotem(int idTotem) {
        Totem t = recuperaTotemPorId(idTotem);
        totens.remove(t);
    }

    public Totem alteraTotem(int idTotem, TotemDTO dadosAlteracaoTotem) {
        Totem t = recuperaTotemPorId(idTotem);
        t.atualizaTotem(dadosAlteracaoTotem);
        return t;
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
        Totem t = recuperaTotemPorId(idTotem);
        return t.getTrancas();
    }

    public List<Bicicleta> recuperaBicicletasDoTotem(int idTotem) {
        List<Tranca> trancas = recuperaTrancasDoTotem(idTotem);
        List<Bicicleta> bicicletas = new ArrayList<>();
        for(Tranca t : trancas) {
            if (t.getBicicleta() != 0) {
                bicicletas.add(bicicletaService.recuperaBicicletaPorId(t.getBicicleta()));
            }
        }

        return bicicletas;
    }

     */


}
