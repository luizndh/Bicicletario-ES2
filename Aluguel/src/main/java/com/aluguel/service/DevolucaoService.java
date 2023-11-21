package com.aluguel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.DevolucaoDTO;
import com.aluguel.dto.NovaDevolucaoDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.Devolucao;
import org.springframework.stereotype.Service;

@Service
public class DevolucaoService {

    @Autowired
    private AluguelService aluguelService;
    
    public Devolucao registraDevolucao(NovaDevolucaoDTO dadosCadastroDevolucao) {
        // TODO: get /tranca/{idTranca}/bicicleta
        BicicletaDTO bicicleta = new BicicletaDTO(Integer.parseInt(dadosCadastroDevolucao.idBicicleta()), "Caloi", "BMX", "1990", 7, "Disponível");

        if (bicicleta.status().equals("nova") || bicicleta.status().equals("em reparo")) {
            // TODO: get /bicicleta/integrarNaRede
            throw new RuntimeException("Bicicleta não integrada na rede");
        }

        Aluguel aluguel = aluguelService.recuperaAluguelPorIdBicicleta(Integer.parseInt(dadosCadastroDevolucao.idBicicleta()));

        String dataFinal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        
        int valorDevido = 10;
        int diffMinutes = 0;
        Date d1;
        Date d2;
        try {
            d1 = format.parse(aluguel.getHoraInicio());
            d2 = format.parse(dataFinal);

            long diff = d2.getTime() - d1.getTime();
            diffMinutes = (int)(diff / (60 * 1000) % 60);
            valorDevido += (diffMinutes / 30)*5;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (diffMinutes >= 30) {
            // TODO: realizar cobrança do valor devido
            // post /cobranca (valorDevido, idCiclista)
        }

        aluguel.atualizaAluguel(new AluguelDTO(aluguel.getBicicleta(), aluguel.getHoraInicio(), Integer.parseInt(dadosCadastroDevolucao.trancaFim()), dataFinal, aluguel.getCobranca(), aluguel.getCiclista(), aluguel.getTrancaInicio()));
        // TODO: atualizar status da bicicleta
        // put /bicicleta/{idBicicleta} (New BicicletaDTO(bicicleta.id(), bicicleta.marca(), bicicleta.modelo(), bicicleta.ano(), bicicleta.numero(), "Disponível"))
        //TODO: atualizar status da tranca solicitando seu fechamento
        // post /tranca/{idTranca}/trancar (idBicicleta)

        //O sistema envia uma mensagem para o ciclista informando os dados da devolução da bicicleta
        //enviarEmail (ciclista.getEmail() , "Devolução registrada" ,
        //        "Olá " + ciclista.getNome() + ",\n\n" +
        //        "Sua devolução foi registrada com sucesso. \n" +
        //        "Seguem os dados da sua devolução: \n\n" +
        //        "Cobrança final: R$" + cobranca.getValor() + "\n" +
        //        "Numero de bicicleta: " + bicicleta.getNumero() + "\n" +
        //        "Marca de bicicleta: " + bicicleta.getMarca() + "\n" +
        //        "Modelo de bicicleta: " + bicicleta.getModelo() + "\n" +
        //        "hora de inicio: " + horaInicio + "\n" +
        //        "hora de fim: " + horaFim + "\n" +
        //        "tranca de retirada: " + trancaInicio.getLocalizacao() + "\n" +
        //        "tranca de devolução: " + trancaFim.getLocalizacao() + "\n\n" +
        //        "Atenciosamente,\n" +
        //        "Equipe Aluguel de Bicicletas");

        Devolucao devolucao = new Devolucao(new DevolucaoDTO(Integer.parseInt(dadosCadastroDevolucao.idBicicleta()), aluguel.getHoraInicio(), Integer.parseInt(dadosCadastroDevolucao.trancaFim()), dataFinal, valorDevido, aluguel.getCiclista()));
        Devolucao.devolucoes.add(devolucao);
        return devolucao;
    }
}
