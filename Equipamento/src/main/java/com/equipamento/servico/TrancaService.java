package com.equipamento.servico;

import static com.equipamento.model.Tranca.trancas;

import com.equipamento.DTO.InclusaoTrancaDTO;
import com.equipamento.DTO.RetiradaTrancaDTO;
import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Tranca.StatusTranca;
import com.equipamento.model.Tranca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrancaService {

    @Autowired
    private TotemService totemService;

    @Autowired
    private BicicletaService bicicletaService;

    public Tranca recuperaTrancaPorId(int idTranca) {
        if(idTranca < 0) {
            throw new IllegalArgumentException("Id da tranca inválido");
        }
        for (Tranca t : trancas) {
            if (t.getId() == idTranca) {
                return t;
            }
        }
        throw new NoSuchElementException("A tranca com id " + idTranca + " não existe");
    }

    public List<Tranca> recuperaTrancas() {
        return trancas;
    }

    //TODO: validar os dados
    public Tranca cadastraTranca(TrancaDTO dadosCadastroTranca) {
        Tranca t = new Tranca(dadosCadastroTranca);
        trancas.add(t);
        //ok
        return t;
    }

    //TODO: validar os dados
    public Tranca alteraTranca(int idTranca, TrancaDTO dadosAlteracaoTranca) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.atualizaTranca(dadosAlteracaoTranca);
        return t;
    }

    //TODO: validar os dados
    public void excluiTranca(int idTranca) {
        Tranca t = recuperaTrancaPorId(idTranca);
        trancas.remove(t);
    }

    public Tranca alteraStatusTranca(int idTranca, StatusTranca acao) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.setStatus(acao);
        return t;
    }

    public Tranca realizarTrancamento(int idTranca, int idBicicleta) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.setStatus(StatusTranca.OCUPADA);
        if(idBicicleta != 0) {
            t.setBicicleta(idBicicleta);
            bicicletaService.alteraStatusBicicleta(idBicicleta, Bicicleta.StatusBicicleta.DISPONIVEL);
        }
        return t;
    }

    public Tranca realizarDestrancamento(int idTranca, int idBicicleta) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.setStatus(StatusTranca.LIVRE);
        if(idBicicleta != 0) {
            t.setBicicleta(0);
            bicicletaService.alteraStatusBicicleta(idBicicleta, Bicicleta.StatusBicicleta.EM_USO);
        }
        return t;
    }

    public void integrarNaRede(InclusaoTrancaDTO dadosInclusao) {
        //TODO: Em caso de repado, verificar se o funcionário é o mesmo que retirou a tranca? depois fazer o que?
        Tranca t = recuperaTrancaPorId(dadosInclusao.idTranca());

        t.setStatus(StatusTranca.LIVRE);
        t.adicionaRegistroNoHistoricoDeInclusao(dadosInclusao);

        //TODO: enviar email para o funcionario informando dados de inclusao - INTEGRACAO
        this.enviaEmailFake(
                this.recuperaEmailDeFuncionarioPorId(dadosInclusao.idFuncionario()),
                "Integrando tranca na rede",
                "Id da tranca: " + dadosInclusao.idTranca() +
                        "Id do totem: " + dadosInclusao.idTotem() +
                        "Id do funcionário: " + dadosInclusao.idFuncionario());
    }

    public void retirarDaRede(RetiradaTrancaDTO dadosRetirada) {
        Tranca t = recuperaTrancaPorId(dadosRetirada.idTranca());
        if(StatusTranca.valueOf(dadosRetirada.statusAcaoReparador()) == StatusTranca.EM_REPARO) {
            t.setStatus(StatusTranca.EM_REPARO);
        } else if(StatusTranca.valueOf(dadosRetirada.statusAcaoReparador()) == StatusTranca.APOSENTADA) {
            t.setStatus(StatusTranca.APOSENTADA);
        } else {
            throw new IllegalArgumentException("Valor inválido para status de tranca");
        }

        t.adicionaRegistroNoHistoricoDeRetirada(dadosRetirada);

        //TODO: enviar email para o funcionario informando dados de inclusao - INTEGRACAO
        this.enviaEmailFake(
                this.recuperaEmailDeFuncionarioPorId(dadosRetirada.idFuncionario()),
                "Integrando tranca na rede",
                "Id da tranca: " + dadosRetirada.idTranca() +
                        "Id do totem: " + dadosRetirada.idTotem() +
                        "Id do funcionário: " + dadosRetirada.idFuncionario() +
                        "Novo status da tranca: " + dadosRetirada.statusAcaoReparador());

    }

    public Bicicleta obterBicicletaNaTranca(int idTranca) {
        Tranca t = recuperaTrancaPorId(idTranca);
        return bicicletaService.recuperaBicicletaPorId(t.getBicicleta());
    }

    private void enviaEmailFake(String email, String assunto, String corpo) {
        return;
    }

    private String recuperaEmailDeFuncionarioPorId(int idFuncionario) {
        return "emailteste@gmail.com";
    }


}
