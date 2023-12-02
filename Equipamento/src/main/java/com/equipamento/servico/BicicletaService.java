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

@Service
public class BicicletaService {

    public Bicicleta recuperaBicicletaPorId(int idBicicleta) {
        if(idBicicleta < 0) {
            throw new IllegalArgumentException("Id da bicicleta invalido");
        }
        for (Bicicleta b : bicicletas) {
            if (b.getId() == idBicicleta) {
                return b;
            }
        }
        throw new NoSuchElementException("A bicicleta com id " + idBicicleta + " nao existe");
    }

    public List<Bicicleta> recuperaBicicletas() {
        return bicicletas;
    }

    public Bicicleta cadastraBicicleta(BicicletaDTO dadosCadastroBicicleta) {
        Bicicleta b = new Bicicleta(dadosCadastroBicicleta);
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

    public void integrarNaRede(InclusaoBicicletaDTO dadosInclusao) {
        Bicicleta b = recuperaBicicletaPorId(dadosInclusao.idBicicleta());
        if(b.getStatus() == StatusBicicleta.EM_USO) {
            //TODO: Redirecionar para UC4 passo 3
            // Fazer chamada para devolver bicicleta do aluguel.
            return;
        }
        b.setStatus(StatusBicicleta.DISPONIVEL);
        b.adicionaRegistroNoHistoricoDeInclusao(dadosInclusao);

        this.enviaEmail(
                this.recuperaEmailDeFuncionarioPorId(dadosInclusao.idFuncionario()),
                "Integrando bicicleta na rede",
                "Id da tranca: " + dadosInclusao.idBicicleta() +
                        "Id do totem: " + dadosInclusao.idTotem() +
                        "Id do funcionário: " + dadosInclusao.idFuncionario());
    }

    public void retirarDaRede(RetiradaBicicletaDTO dadosRetirada) {
        Bicicleta b = recuperaBicicletaPorId(dadosRetirada.idBicicleta());
        if(StatusBicicleta.valueOf(dadosRetirada.statusAcaoReparador()) == StatusBicicleta.EM_REPARO) {
            b.setStatus(StatusBicicleta.EM_REPARO);
        } else if(StatusBicicleta.valueOf(dadosRetirada.statusAcaoReparador()) == StatusBicicleta.APOSENTADA) {
            b.setStatus(StatusBicicleta.APOSENTADA);
        } else {
            throw new IllegalArgumentException("Valor inválido para status de tranca");
        }
        b.adicionaRegistroNoHistoricoDeRetirada(dadosRetirada);

        this.enviaEmail(
                this.recuperaEmailDeFuncionarioPorId(dadosRetirada.idFuncionario()),
                "Integrando tranca na rede",
                        "Id da tranca: " + dadosRetirada.idTranca() +
                        "Id da bicicleta: " + dadosRetirada.idBicicleta() +
                        "Id do funcionário: " + dadosRetirada.idFuncionario() +
                        "Novo status da bicicleta: " + dadosRetirada.statusAcaoReparador());
    }

    private void enviaEmail(String email, String assunto, String mensagem) {
        ObjectMapper mapper = new ObjectMapper();
        EmailDTO novoEmail = new EmailDTO(email, assunto, mensagem);

        try {
            String jsonEntrada = mapper.writeValueAsString(novoEmail);

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8081/enviarEmail"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEntrada))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            EmailResponseDTO emailResponse = mapper.readValue(response.body(), EmailResponseDTO.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String recuperaEmailDeFuncionarioPorId(int idFuncionario) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("localhost:8082/funcionario/" + idFuncionario))
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
