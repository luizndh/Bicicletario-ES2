package com.aluguel.service;

import com.aluguel.DTO.AluguelDTO;
import com.aluguel.DTO.BicicletaDTO;
import com.aluguel.DTO.TrancaDTO;
import com.aluguel.model.Aluguel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import static com.aluguel.model.Aluguel.alugueis;

public class AluguelService {
    @Autowired
    CiclistaService ciclistaService;

    public List<Aluguel> recuperaAlugueis() { return alugueis; }

    public Aluguel registraAluguel(String ciclista, String trancaInicio) {
        // TODO: get /tranca/{idTranca}
        TrancaDTO tranca = new TrancaDTO(1, 1, 10, "Urca", "2010", "TrancaPika", "Ocupada");
        // TODO: get /tranca/{idTranca}/bicicleta
        BicicletaDTO bicicleta = new BicicletaDTO(1, "Caloi", "BMX", "1990", 7, "Disponível");

        ciclistaService.verificaSeCiclistaPodeAlugar(Integer.parseInt(ciclista));
            
            
        if (bicicleta.status() == "Indicada para reparo") {
            throw new RuntimeException("Bicicleta indicada para reparo");
        }

        // TODO: enviar cobrança dos R$10 iniciais para administradora de cartão de crédito
        
        String horaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        AluguelDTO dadosRegistroAluguel = new AluguelDTO(bicicleta.id(), horaInicio, -1, "", 10, Integer.parseInt(ciclista), tranca.id());
        Aluguel aluguel = new Aluguel(dadosRegistroAluguel);
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
        if (!alugueis.isEmpty()) {
            for (Aluguel aluguel : alugueis) {
                if (aluguel.getBicicleta() == idBicicleta) {
                    return aluguel;
                }
            }
        }
        throw new IllegalArgumentException("A bicicleta com id " + idBicicleta + " não está em aluguel");
    }
}
