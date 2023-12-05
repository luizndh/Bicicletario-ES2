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
        System.out.println("integrando bicicleta na rede...");
        Bicicleta b = recuperaBicicletaPorId(dadosInclusao.idBicicleta());
        if(b.getStatus() == StatusBicicleta.EM_USO) {
            DevolucaoBicicletaDTO devolucaoDTO = new DevolucaoBicicletaDTO(dadosInclusao.idTranca(), dadosInclusao.idBicicleta());
            this.chamaDevolucaoBicicleta(devolucaoDTO);
            return false;
        }
        b.setStatus(StatusBicicleta.DISPONIVEL);
        b.adicionaRegistroNoHistoricoDeInclusao(dadosInclusao);

        System.out.println("RECUPERANDO EMAIL DO FUNCIONARIO...");
        String emailFuncionario = this.recuperaEmailDeFuncionarioPorId(dadosInclusao.idFuncionario());
        System.out.println("EMAIL DO FUNCIONARIO: " + emailFuncionario);

        return this.enviaEmail(
                emailFuncionario,
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

        String emailFuncionario = this.recuperaEmailDeFuncionarioPorId(dadosRetirada.idFuncionario());

        return this.enviaEmail(
                emailFuncionario,
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
            System.out.println("JSON ENTRADA DE EMAIL: " );
            System.out.println(jsonEntrada);

            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_EXTERNO + "/enviarEmail"))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonEntrada))
                    .header("Content-Type", "application/json")
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("JSON SAIDA DE EMAIL: ");
            System.out.println(response.body());
            System.out.println("STATUS CODE: " + response.statusCode());
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
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL_ALUGUEL + "/funcionario/" + idFuncionario))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("BODY DA RESPOSTA: " + response.body());
            System.out.println(response.body());
            System.out.println("STATUS CODE: " + response.statusCode());
            if(response.statusCode() == 422) {
                System.out.println("lancando illegal argument exception...");
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
