package com.equipamento.servico;

import com.equipamento.dto.*;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Totem;
import com.equipamento.model.Tranca;
import org.springframework.stereotype.Service;

@Service
public class IntegracaoService {

    TotemService totemService = new TotemService();
    BicicletaService bicicletaService = new BicicletaService();
    TrancaService trancaService = new TrancaService();

    public void restaurarDados() {
        Totem.totens.clear();
        Tranca.trancas.clear();
        Bicicleta.bicicletas.clear();

        //Totem
        totemService.cadastraTotem(new TotemDTO("Rio de Janeiro", ""));

        //Bicicleta
        BicicletaDTO bicicleta1 = new BicicletaDTO("Caloi", "Caloi", "2020", 12345, "DISPONIVEL");
        BicicletaDTO bicicleta2 = new BicicletaDTO("Caloi", "Caloi", "2020", 12345, "REPARO_SOLICITADO");
        BicicletaDTO bicicleta3 = new BicicletaDTO("Caloi", "Caloi", "2020", 12345, "EM_USO");
        BicicletaDTO bicicleta4 = new BicicletaDTO("Caloi", "Caloi", "2020", 12345, "EM_REPARO");
        BicicletaDTO bicicleta5 = new BicicletaDTO("Caloi", "Caloi", "2020", 12345, "EM_USO");

        bicicletaService.cadastraBicicleta(bicicleta1);
        bicicletaService.cadastraBicicleta(bicicleta2);
        bicicletaService.cadastraBicicleta(bicicleta3);
        bicicletaService.cadastraBicicleta(bicicleta4);
        bicicletaService.cadastraBicicleta(bicicleta5);

        RetiradaBicicletaDTO retiradaBicicletaDTO = new RetiradaBicicletaDTO(0, 4, 1, "Reparo de bicicleta");

        Bicicleta.bicicletas.get(3).adicionaRegistroNoHistoricoDeRetirada(retiradaBicicletaDTO);

        //Tranca
        TrancaDTO tranca1 = new TrancaDTO(12345, "Rio de Janeiro", "2020", "Caloi", "OCUPADA");
        TrancaDTO tranca2 = new TrancaDTO(12345, "Rio de Janeiro", "2020", "Caloi", "LIVRE");
        TrancaDTO tranca3 = new TrancaDTO(12345, "Rio de Janeiro", "2020", "Caloi", "OCUPADA");
        TrancaDTO tranca4 = new TrancaDTO(12345, "Rio de Janeiro", "2020", "Caloi", "OCUPADA");
        TrancaDTO tranca5 = new TrancaDTO(12345, "Rio de Janeiro", "2020", "Caloi", "EM_REPARO");
        TrancaDTO tranca6 = new TrancaDTO(12345, "Rio de Janeiro", "2020", "Caloi", "REPARO_SOLICITADO");

        trancaService.cadastraTranca(tranca1);
        Tranca.trancas.get(0).setBicicleta(1);
        Tranca.trancas.get(0).setTotem(1);

        trancaService.cadastraTranca(tranca2);
        Tranca.trancas.get(1).setTotem(1);

        trancaService.cadastraTranca(tranca3);
        Tranca.trancas.get(2).setBicicleta(2);
        Tranca.trancas.get(2).setTotem(1);

        trancaService.cadastraTranca(tranca4);
        Tranca.trancas.get(3).setBicicleta(5);
        Tranca.trancas.get(3).setTotem(1);

        trancaService.cadastraTranca(tranca5);

        trancaService.cadastraTranca(tranca6);
        Tranca.trancas.get(5).setTotem(1);

        RetiradaTrancaDTO retiradaTrancaDTO = new RetiradaTrancaDTO(0, 5, 1, "Reparo de tranca");
        Tranca.trancas.get(4).adicionaRegistroNoHistoricoDeRetirada(retiradaTrancaDTO);
    }
}
