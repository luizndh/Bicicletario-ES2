package com.equipamento.servico;

import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.Tranca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class TrancaServiceTest {

    /*
    @InjectMocks
    TrancaService trancaService;

    @Mock
    Tranca tranca;

    @Spy
    List<Tranca> trancas;

    @BeforeEach
    public void setUp() {
        // Arrange

        TrancaDTO trancaDTO1 = new TrancaDTO(1, "localizacao teste1", "2021", "modelo teste1", "NOVA");
        TrancaDTO trancaDTO2 = new TrancaDTO(2, "localizacao teste2", "2022", "modelo teste2", "LIVRE");
        TrancaDTO trancaDTO3 = new TrancaDTO(3, "localizacao teste3", "2023", "modelo teste3", "APOSENTADA");

        trancas = new ArrayList<>();

        trancas.add(new Tranca(trancaDTO1));
        trancas.add(new Tranca(trancaDTO2));
        trancas.add(new Tranca(trancaDTO3));
    }


    @Test
    public void testRecuperaTrancaPorId() {
        // Arrange
        int id = trancas.get(0).getId();

        // Act + Assert
        Tranca tranca = this.trancaService.recuperaTrancaPorId(id);

        // Assert
        assertNotNull(tranca);
    }

*/
}
