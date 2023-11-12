package com.externo.servico;

import com.externo.DTO.CobrancaDTO;
import com.externo.DTO.EmailDTO;
import com.externo.model.Cobranca;
import org.springframework.stereotype.Service;

import static com.externo.model.Cobranca.cobrancas;

import java.util.List;

@Service
public class CobrancaService {

    /*
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
    */

    public boolean realizaCobranca(CobrancaDTO dadosCobranca) {

        //TODO random number?
        return true;
    }

    public boolean processaCobrancasEmFila() {
        for (Cobranca c : cobrancas) {

            cobrancas.remove(c);
            //TODO chamar realizaCobranca ? e ver docs
        }

        return true;
    }

    public boolean incluiFilaCobranca(CobrancaDTO dadosCobranca) {
        cobrancas.add(new Cobranca(dadosCobranca));
        //TODO só trocar o status pra pendente
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
