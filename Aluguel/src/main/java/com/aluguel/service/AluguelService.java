package com.aluguel.service;

import com.aluguel.Integracoes;
import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.CobrancaDTO;
import com.aluguel.dto.NovaCobrancaDTO;
import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.dto.TrancaDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.Ciclista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.aluguel.model.Aluguel.alugueis;

@Service
public class AluguelService {

    @Autowired
    Integracoes integracoes;

    @Autowired
    CiclistaService ciclistaService;

    public List<Aluguel> recuperaAlugueis() { return alugueis; }

    public void restauraDados() {
        alugueis.clear();

        String horaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
        String duasHorasAtras = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis() - 7200000));

        Aluguel aluguel1 = new Aluguel(new NovoAluguelDTO("3", "2"));
        aluguel1.atualizaAluguel(new AluguelDTO("3", horaInicio, "-1", "-1", "1", "3", "2", "EM_ANDAMENTO"));
        alugueis.add(aluguel1);

        Aluguel aluguel2 = new Aluguel(new NovoAluguelDTO("4", "4"));
        aluguel2.atualizaAluguel(new AluguelDTO("5", duasHorasAtras, "-1", "-1", "2", "4", "4", "EM_ANDAMENTO"));
        alugueis.add(aluguel2);

        Aluguel aluguel3 = new Aluguel(new NovoAluguelDTO("3", "1"));
        aluguel3.atualizaAluguel(new AluguelDTO("1", duasHorasAtras, "2", horaInicio, "3", "3", "1", "FINALIZADO_COM_COBRANCA_EXTRA_PENDENTE"));
        alugueis.add(aluguel3);
    }

    public Aluguel registraAluguel(NovoAluguelDTO dadosCadastroAluguel) {
        Ciclista ciclista = ciclistaService.recuperaCiclistaPorId(Integer.parseInt(dadosCadastroAluguel.ciclista()));

        TrancaDTO trancaInicio = integracoes.recuperaTrancaPorId(Integer.parseInt(dadosCadastroAluguel.trancaInicio()));

        BicicletaDTO bicicleta = integracoes.recuperaBicicletaPorId(trancaInicio.bicicleta());

        ciclistaService.verificaSeCiclistaPodeAlugar(ciclista.getId());
            
            
        if (bicicleta.status().equals("Indicada para reparo")) {
            throw new IllegalArgumentException("Bicicleta indicada para reparo");
        }

        CobrancaDTO cobranca = integracoes.realizaCobranca(new NovaCobrancaDTO(String.valueOf(10.00), String.valueOf(ciclista.getId())));
        
        String horaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Aluguel aluguel = new Aluguel(dadosCadastroAluguel);
        aluguel.atualizaAluguel(new AluguelDTO(String.valueOf(bicicleta.id()), horaInicio, "-1", "-1", "10", String.valueOf(ciclista.getId()), String.valueOf(trancaInicio.id()), "EM_ANDAMENTO"));
        alugueis.add(aluguel);

        integracoes.destrancaTranca(trancaInicio.id(), Integer.parseInt(bicicleta.id()));

        integracoes.enviaEmail(ciclista.getEmail() , "Aluguel registrado" ,
               "Olá " + ciclista.getNome() + ",\n\n" +
               "Seu aluguel foi registrado com sucesso. \n" +
               "Seguem os dados do seu aluguel: \n\n" +
               "Cobrança inicial: R$" + cobranca.valor() + "\n" +
               "Numero de bicicleta: " + bicicleta.numero() + "\n" +
               "Marca de bicicleta: " + bicicleta.marca() + "\n" +
               "Modelo de bicicleta: " + bicicleta.modelo() + "\n" +
               "Hora de inicio: " + horaInicio + "\n" +
               "Tranca de retirada: " + trancaInicio.localizacao() + "\n\n" +
               "Atenciosamente,\n" +
               "Equipe Aluguel de Bicicletas");

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

    public static Aluguel recuperaAluguelPorIdCiclista(int idCiclista) {
        for (Aluguel aluguel : alugueis) {
            if (aluguel.getCiclista() == idCiclista) {
                return aluguel;
            }
        }
        throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não está em aluguel");
    }
}
