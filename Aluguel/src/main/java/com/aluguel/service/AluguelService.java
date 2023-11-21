package com.aluguel.service;

import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.dto.TrancaDTO;
import com.aluguel.model.Aluguel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.aluguel.model.Aluguel.alugueis;

@Service
public class AluguelService {
    @Autowired
    CiclistaService ciclistaService;

    public List<Aluguel> recuperaAlugueis() { return alugueis; }

    public Aluguel registraAluguel(NovoAluguelDTO dadosCadastroAluguel) {
        // TODO: get /tranca/{idTranca}
        TrancaDTO tranca = new TrancaDTO(1, 1, 10, "Urca", "2010", "Tranca Teste", "Ocupada");
        // TODO: get /tranca/{idTranca}/bicicleta
        BicicletaDTO bicicleta = new BicicletaDTO(1, "Caloi", "BMX", "1990", 7, "Disponível");

        ciclistaService.verificaSeCiclistaPodeAlugar(Integer.parseInt(dadosCadastroAluguel.ciclista()));
            
            
        if (bicicleta.status().equals("Indicada para reparo")) {
            throw new IllegalArgumentException("Bicicleta indicada para reparo");
        }

        // TODO: enviar cobrança dos R$10 iniciais para administradora de cartão de crédito
        
        String horaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Aluguel aluguel = new Aluguel(dadosCadastroAluguel);
        aluguel.atualizaAluguel(new AluguelDTO(bicicleta.id(), horaInicio, -1, "", 10, Integer.parseInt(dadosCadastroAluguel.ciclista()), tranca.id()));
        // Caso fosse inserção em um banco de dados, seria chamada uma função de persistência aqui. Essa função seria sobreescrita nos testes.
        alugueis.add(aluguel);

        // TODO: atualizar status da bicicleta
        // TODO: solicitar abertura da tranca

        // TODO: envia email para o ciclista com os dados do aluguel
        ///enviarEmail (ciclista.getEmail() , "Aluguel registrado" ,
        //        "Olá " + ciclista.getNome() + ",\n\n" +
        //        "Seu aluguel foi registrado com sucesso. \n" +
        //        "Seguem os dados do seu aluguel: \n\n" +
        //        "Cobrança inicial: R$" + cobranca.getValor() + "\n" +
        //        "Numero de bicicleta: " + bicicleta.getNumero() + "\n" +
        //        "Marca de bicicleta: " + bicicleta.getMarca() + "\n" +
        //        "Modelo de bicicleta: " + bicicleta.getModelo() + "\n" +
        //        "hora de inicio: " + horaInicio + "\n" +
        //        "tranca de retirada: " + trancaInicio.getLocalizacao() + "\n\n" +
        //        "Atenciosamente,\n" +
        //        "Equipe Aluguel de Bicicletas");

        return aluguel;
    }

    public Aluguel recuperaAluguelPorIdBicicleta(int idBicicleta) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getBicicleta() == idBicicleta) {
                return aluguel;
            }
        }
        throw new IllegalArgumentException("A bicicleta com id " + idBicicleta + " não está em aluguel");
    }
}
