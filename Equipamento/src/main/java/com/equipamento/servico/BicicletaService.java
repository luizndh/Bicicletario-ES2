package com.equipamento.servico;

import com.equipamento.dto.*;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Bicicleta.StatusBicicleta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;

import static com.equipamento.model.Bicicleta.bicicletas;
import static com.equipamento.util.Constantes.URL_ALUGUEL;
import static com.equipamento.util.Constantes.URL_EXTERNO;

@Service
public class BicicletaService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public Bicicleta recuperaBicicletaPorId(int idBicicleta) {
        if(idBicicleta < 0) {
            throw new IllegalArgumentException();
        }
        for (Bicicleta b : bicicletas) {
            if (b.getId() == idBicicleta) {
                return b;
            }
        }
        throw new NoSuchElementException();
    }

    public List<Bicicleta> recuperaBicicletas() {
        return bicicletas;
    }

    public Bicicleta cadastraBicicleta(BicicletaDTO dadosCadastroBicicleta) {
        Bicicleta b = new Bicicleta(dadosCadastroBicicleta);
        b.setStatus(StatusBicicleta.NOVA);
        bicicletas.add(b);
        return b;
    }

    public Bicicleta alteraBicicleta(int idBicicleta, BicicletaDTO dadosAlteracaoBicicleta) {
        Bicicleta b = recuperaBicicletaPorId(idBicicleta);
        b.atualizaBicicleta(dadosAlteracaoBicicleta);
        return b;
    }

    public void excluiBicicleta(int idBicicleta) {
        Bicicleta b = recuperaBicicletaPorId(idBicicleta);
        bicicletas.remove(b);
    }

    public Bicicleta alteraStatusBicicleta(int idBicicleta, String acao) {
        Bicicleta b = recuperaBicicletaPorId(idBicicleta);
        b.setStatus(StatusBicicleta.valueOf(acao));
        return b;
    }

    public boolean integrarNaRede(InclusaoBicicletaDTO dadosInclusao) {
        Bicicleta b = recuperaBicicletaPorId(dadosInclusao.idBicicleta());
        if(b.getStatus() == StatusBicicleta.EM_USO) {
            DevolucaoBicicletaDTO devolucaoDTO = new DevolucaoBicicletaDTO(dadosInclusao.idTranca(), dadosInclusao.idBicicleta());
            this.chamaDevolucaoBicicleta(devolucaoDTO);
            return false;
        }
        b.setStatus(StatusBicicleta.DISPONIVEL);
        b.adicionaRegistroNoHistoricoDeInclusao(dadosInclusao);

        return this.enviaEmail(
                this.recuperaEmailDeFuncionarioPorId(dadosInclusao.idFuncionario()),
                "Integrando bicicleta na rede",
                "Id da tranca: " + dadosInclusao.idBicicleta() +
                        "Id da tranca: " + dadosInclusao.idTranca() +
                        "Id do funcionário: " + dadosInclusao.idFuncionario());
    }

    private void chamaDevolucaoBicicleta(DevolucaoBicicletaDTO devolucaoBicicletaDTO) {
        try {
            String jsonEntrada = mapper.writeValueAsString(devolucaoBicicletaDTO);

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_ALUGUEL + "/devolucao"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEntrada))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean retirarDaRede(RetiradaBicicletaDTO dadosRetirada) {
        Bicicleta b = recuperaBicicletaPorId(dadosRetirada.idBicicleta());
        if(StatusBicicleta.valueOf(dadosRetirada.statusAcaoReparador()) == StatusBicicleta.EM_REPARO) {
            b.setStatus(StatusBicicleta.EM_REPARO);
        } else if(StatusBicicleta.valueOf(dadosRetirada.statusAcaoReparador()) == StatusBicicleta.APOSENTADA) {
            b.setStatus(StatusBicicleta.APOSENTADA);
        } else {
            throw new IllegalArgumentException();
        }
        b.adicionaRegistroNoHistoricoDeRetirada(dadosRetirada);

        return this.enviaEmail(
                this.recuperaEmailDeFuncionarioPorId(dadosRetirada.idFuncionario()),
                "Retirando bicicleta da rede",
                        "Id da tranca: " + dadosRetirada.idTranca() +
                        "Id da bicicleta: " + dadosRetirada.idBicicleta() +
                        "Id do funcionário: " + dadosRetirada.idFuncionario() +
                        "Novo status da bicicleta: " + dadosRetirada.statusAcaoReparador());
    }

    private boolean enviaEmail(String email, String assunto, String mensagem) {
        EmailDTO novoEmail = new EmailDTO(email, assunto, mensagem);

        try {
            String jsonEntrada = mapper.writeValueAsString(novoEmail);

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_EXTERNO + "/enviarEmail"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEntrada))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String recuperaEmailDeFuncionarioPorId(int idFuncionario) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_ALUGUEL + "/funcionario/" + idFuncionario))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            FuncionarioDTO funcionarioResponse = mapper.readValue(response.body(), FuncionarioDTO.class);
            return funcionarioResponse.email();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
