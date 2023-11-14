package com.equipamento.servico;

import com.equipamento.DTO.BicicletaDTO;
import com.equipamento.DTO.InclusaoBicicletaDTO;
import com.equipamento.DTO.RetiradaBicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Bicicleta.StatusBicicleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.equipamento.model.Bicicleta.bicicletas;

@Service
public class BicicletaService {

    @Autowired
    private TotemService totemService;

    public Bicicleta recuperaBicicletaPorId(int idBicicleta) {
        if(idBicicleta < 0) {
            throw new IllegalArgumentException("Id da bicicleta invalido");
        }
        System.out.println("PROCURANDO PELAS BICICLETAS...");
        for (Bicicleta b : bicicletas) {
            if (b.getId() == idBicicleta) {
                return b;
            }
        }
        System.out.println("ANTES DE VERIFICAR SE A BICICILETA EXISTE...");
        throw new NoSuchElementException("A bicicleta com id " + idBicicleta + " nao existe");
    }

    /*
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


     */
    public Bicicleta alteraStatusBicicleta(int idBicicleta, StatusBicicleta acao) {
        Bicicleta b = recuperaBicicletaPorId(idBicicleta);
        b.setStatus(acao);
        return b;
    }

    public void integrarNaRede(InclusaoBicicletaDTO dadosInclusao) {
        //TODO: Em caso de repado, verificar se o funcionário é o mesmo que retirou a tranca? depois fazer o que?
        Bicicleta b = recuperaBicicletaPorId(dadosInclusao.idBicicleta());
        if(b.getStatus() == StatusBicicleta.EM_USO) {
            //TODO: Redirecionar para UC4 passo 3
            return;
        }
        b.setStatus(StatusBicicleta.DISPONIVEL);
        b.adicionaRegistroNoHistoricoDeInclusao(dadosInclusao);

        //TODO: enviar email para o funcionario informando dados de inclusao - INTEGRACAO
        this.enviaEmailFake(
                this.recuperaEmailDeFuncionarioPorId(dadosInclusao.idFuncionario()),
                "Integrando bicicleta na rede",
                "Id da tranca: " + dadosInclusao.idBicicleta() +
                        "Id do totem: " + dadosInclusao.idTotem() +
                        "Id do funcionário: " + dadosInclusao.idFuncionario());
    }

    /*
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

        // Salva os dados de retirada
        //TODO: enviar email para o funcionario informando dados de retirada - INTEGRACAO
        this.enviaEmailFake(
                this.recuperaEmailDeFuncionarioPorId(dadosRetirada.idFuncionario()),
                "Integrando tranca na rede",
                "Id da tranca: " + dadosRetirada.idTranca() +
                        "Id da bicicleta: " + dadosRetirada.idBicicleta() +
                        "Id do funcionário: " + dadosRetirada.idFuncionario() +
                        "Novo status da bicicleta: " + dadosRetirada.statusAcaoReparador());

    }
     */
    private void enviaEmailFake(String email, String assunto, String corpo) {
        return;
    }

    private String recuperaEmailDeFuncionarioPorId(int idFuncionario) {
        return "emailteste@gmail.com";
    }


}
