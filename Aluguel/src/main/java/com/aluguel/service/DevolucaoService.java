package com.aluguel.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aluguel.Integracoes;
import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.CobrancaDTO;
import com.aluguel.dto.DevolucaoDTO;
import com.aluguel.dto.NovaCobrancaDTO;
import com.aluguel.dto.NovaDevolucaoDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.model.Ciclista;
import com.aluguel.model.Devolucao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevolucaoService {

    @Autowired
    Integracoes integracoes;

    @Autowired
    AluguelService aluguelService;

    @Autowired
    CiclistaService ciclistaService;

    @Autowired
    CartaoDeCreditoService cartaoDeCreditoService;
    
    public Devolucao registraDevolucao(NovaDevolucaoDTO dadosCadastroDevolucao) {
        integracoes.recuperaBicicletaPorId(Integer.parseInt(dadosCadastroDevolucao.idBicicleta()));

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

        CobrancaDTO cobranca = new CobrancaDTO("", "", "", "", "");

        if (diffMinutes >= 30) { 
            NovaCobrancaDTO novaCobranca = new NovaCobrancaDTO(String.valueOf(valorDevido), String.valueOf(aluguel.getCiclista()));
            cobranca = integracoes.realizaCobranca(novaCobranca);
        }

        aluguel.atualizaAluguel(new AluguelDTO(String.valueOf(aluguel.getBicicleta()), aluguel.getHoraInicio(), dadosCadastroDevolucao.trancaFim(), dataFinal, String.valueOf(aluguel.getCobranca()), String.valueOf(aluguel.getCiclista()), String.valueOf(aluguel.getTrancaInicio()), "FINALIZADO_COM_COBRANCA_EXTRA_PENDENTE"));
        integracoes.trancaTranca(aluguel.getTrancaFim(), aluguel.getBicicleta());

        Ciclista ciclista = ciclistaService.recuperaCiclistaPorId(aluguel.getCiclista());
        CartaoDeCredito cartao = cartaoDeCreditoService.recuperaCartaoDeCreditoPorId(ciclista.getId());
        String horaFim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        integracoes.enviaEmail (ciclista.getEmail() , "Devolução registrada" ,
               "Olá " + ciclista.getNome() + ",\n\n" +
               "Sua devolução foi registrada com sucesso. \n" +
               "Seguem os dados da sua devolução: \n\n" +
               "Hora de devolução: " + horaFim + "\n" +
               "Hora de cobrança: " + horaFim + "\n" +
               "Valor extra do aluguel: R$" + cobranca.valor() + "\n" +
               "Cartão usado para cobrança: " + cartao.getNumero() + "\n" +
               "Numero de bicicleta: " + aluguel.getBicicleta() + "\n" +
               "Numero da tranca de devolução: " + aluguel.getTrancaFim() + "\n\n" +
               "Atenciosamente,\n" +
               "Equipe Aluguel de Bicicletas");

        Devolucao devolucao = new Devolucao(new DevolucaoDTO(dadosCadastroDevolucao.idBicicleta(), aluguel.getHoraInicio(), dadosCadastroDevolucao.trancaFim(), dataFinal, String.valueOf(valorDevido), String.valueOf(aluguel.getCiclista())));
        Devolucao.devolucoes.add(devolucao);
        return devolucao;
    }
}
