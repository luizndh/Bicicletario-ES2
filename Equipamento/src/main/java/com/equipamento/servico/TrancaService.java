package com.equipamento.servico;

import static com.equipamento.model.Tranca.trancas;
import static com.equipamento.util.Constantes.URL_ALUGUEL;
import static com.equipamento.util.Constantes.URL_EXTERNO;

import com.equipamento.dto.*;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Tranca.StatusTranca;
import com.equipamento.model.Tranca;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrancaService {

    @Autowired
    private BicicletaService bicicletaService;

    public Tranca recuperaTrancaPorId(int idTranca) {
        if(idTranca < 0) {
            throw new IllegalArgumentException();
        }
        for (Tranca t : trancas) {
            if (t.getId() == idTranca) {
                return t;
            }
        }
        throw new NoSuchElementException();
    }

    public List<Tranca> recuperaTrancas() {
        return trancas;
    }

    public Tranca cadastraTranca(TrancaDTO dadosCadastroTranca) {
        Tranca t = new Tranca(dadosCadastroTranca);
        trancas.add(t);
        return t;
    }

    public Tranca alteraTranca(int idTranca, TrancaDTO dadosAlteracaoTranca) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.atualizaTranca(dadosAlteracaoTranca);
        return t;
    }

    public void excluiTranca(int idTranca) {
        Tranca t = recuperaTrancaPorId(idTranca);
        trancas.remove(t);
    }

    public Tranca alteraStatusTranca(int idTranca, String acao) {
        Tranca t = recuperaTrancaPorId(idTranca);
        if(acao.equals("TRANCAR")) {
            t.setStatus(StatusTranca.OCUPADA);
        } else if(acao.equals("DESTRANCAR")) {
            t.setStatus(StatusTranca.LIVRE);
        } else {
            throw new IllegalArgumentException();
        }
        return t;
    }

    public Tranca realizarTrancamento(int idTranca, int idBicicleta) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.setStatus(StatusTranca.OCUPADA);
        if(idBicicleta != 0) {
            t.setBicicleta(idBicicleta);
            bicicletaService.alteraStatusBicicleta(idBicicleta, "DISPONIVEL");
        }
        return t;
    }

    public Tranca realizarDestrancamento(int idTranca, int idBicicleta) {
        Tranca t = recuperaTrancaPorId(idTranca);
        t.setStatus(StatusTranca.LIVRE);
        if(idBicicleta != 0) {
            t.setBicicleta(0);
            bicicletaService.alteraStatusBicicleta(idBicicleta, "EM_USO");
        }
        return t;
    }

    public boolean integrarNaRede(InclusaoTrancaDTO dadosInclusao) {
        Tranca t = recuperaTrancaPorId(dadosInclusao.idTranca());

        t.setStatus(StatusTranca.LIVRE);
        t.adicionaRegistroNoHistoricoDeInclusao(dadosInclusao);

        String emailFuncionario = this.recuperaEmailDeFuncionarioPorId(dadosInclusao.idFuncionario());

        return this.enviaEmail(
                emailFuncionario,
                "Integrando tranca na rede",
                "Id da tranca: " + dadosInclusao.idTranca() +
                        "Id do totem: " + dadosInclusao.idTotem() +
                        "Id do funcionário: " + dadosInclusao.idFuncionario());
    }

    public boolean retirarDaRede(RetiradaTrancaDTO dadosRetirada) {
        Tranca t = recuperaTrancaPorId(dadosRetirada.idTranca());
        if(StatusTranca.valueOf(dadosRetirada.statusAcaoReparador()) == StatusTranca.EM_REPARO) {
            t.setStatus(StatusTranca.EM_REPARO);
        } else if(StatusTranca.valueOf(dadosRetirada.statusAcaoReparador()) == StatusTranca.APOSENTADA) {
            t.setStatus(StatusTranca.APOSENTADA);
        } else {
            throw new IllegalArgumentException();
        }

        t.adicionaRegistroNoHistoricoDeRetirada(dadosRetirada);

        String emailFuncionario = this.recuperaEmailDeFuncionarioPorId(dadosRetirada.idFuncionario());

        return this.enviaEmail(
                emailFuncionario,
                "Retirando tranca da rede",
                "Id da tranca: " + dadosRetirada.idTranca() +
                        "Id do totem: " + dadosRetirada.idTotem() +
                        "Id do funcionário: " + dadosRetirada.idFuncionario() +
                        "Novo status da tranca: " + dadosRetirada.statusAcaoReparador());

    }

    public Bicicleta obterBicicletaNaTranca(int idTranca) {
        Tranca t = recuperaTrancaPorId(idTranca);
        return bicicletaService.recuperaBicicletaPorId(t.getBicicleta());
    }

    private boolean enviaEmail(String email, String assunto, String mensagem) {
        ObjectMapper mapper = new ObjectMapper();
        EmailDTO novoEmail = new EmailDTO(email, assunto, mensagem);

        try {
            String jsonEntrada = mapper.writeValueAsString(novoEmail);

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_EXTERNO + "/enviarEmail"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEntrada))
                    .header("Content-Type", "application/json")
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200) {
                return true;
            } else if(response.statusCode() == 404) {
                throw new NoSuchElementException();
            } else if(response.statusCode() == 422) {
                throw new IllegalArgumentException();
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private String recuperaEmailDeFuncionarioPorId(int idFuncionario) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_ALUGUEL + "/funcionario/" + idFuncionario))
                    .GET()
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 422) {
                throw new IllegalArgumentException();
            } else if(response.statusCode() == 404) {
                throw new NoSuchElementException();
            } else if(response.statusCode() == 200) {
                FuncionarioDTO funcionarioResponse = mapper.readValue(response.body(), FuncionarioDTO.class);
                return funcionarioResponse.email();
            }
            return null;
        } catch(Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
