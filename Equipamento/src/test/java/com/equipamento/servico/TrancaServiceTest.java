package com.equipamento.servico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.TrancaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Tranca;
import com.equipamento.model.Bicicleta.StatusBicicleta;
import com.equipamento.model.Tranca.StatusTranca;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrancaServiceTest {

    @InjectMocks
    TrancaService trancaService;

    @Mock
    BicicletaService bicicletaService;

    @Mock
    Tranca t;

    Bicicleta bicicleta;

    @Mock
    Bicicleta bicicletaMock;

    @BeforeEach
    public void setUp() {
        // Arrange
        Bicicleta bicicleta1 = new Bicicleta(new BicicletaDTO("marca teste1", "modelo teste1", "2020", 1, "NOVA"));
        Bicicleta bicicleta2 = new Bicicleta(new BicicletaDTO("marca teste2", "modelo teste2", "2021", 2, "NOVA"));
        Bicicleta bicicleta3 = new Bicicleta(new BicicletaDTO("marca teste3", "modelo teste3", "2022", 3, "EM_USO"));

        Bicicleta.bicicletas.add(bicicleta1);
        Bicicleta.bicicletas.add(bicicleta2);
        Bicicleta.bicicletas.add(bicicleta3);

        TrancaDTO trancaDTO1 = new TrancaDTO(1, "localizacao teste1", "2021", "modelo teste1", "NOVA");
        TrancaDTO trancaDTO2 = new TrancaDTO(2, "localizacao teste2", "2022", "modelo teste2", "LIVRE");
        TrancaDTO trancaDTO3 = new TrancaDTO(3, "localizacao teste3", "2023", "modelo teste3", "APOSENTADA");

        
        Tranca.trancas = new ArrayList<>();

        Tranca t = new Tranca(trancaDTO1);
        t.setBicicleta(2);

        Tranca.trancas.add(t);
        Tranca.trancas.add(new Tranca(trancaDTO2));
        Tranca.trancas.add(new Tranca(trancaDTO3));
    }

    @Test
    void testRecuperaTrancaPorIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> trancaService.recuperaTrancaPorId(-1));
    }

    @Test
    void testRecuperaBicicletaPorIdQueNaoExiste() {
        assertThrows(NoSuchElementException.class, () -> trancaService.recuperaTrancaPorId(10));
    }

    @Test
    void testRecuperaTrancaPorId() {
        // Act
        Tranca tranca = trancaService.recuperaTrancaPorId(1);

        // Assert
        assertNotNull(tranca);
        assertEquals(tranca.getBicicleta(), 2);
    }

    @Test
    void testAlteraStatusTranca() {
        // Act
        Tranca novaTranca = trancaService.alteraStatusTranca(1, StatusTranca.OCUPADA);

        // Assert
        assertNotNull(novaTranca);
        assertEquals(novaTranca.getStatus(), StatusTranca.OCUPADA);
    }

    @Test
    void testRealizaTrancamentoTrancaQuandoBicicletaNaoExiste() {
        // Act
        Tranca novaTranca = trancaService.realizarTrancamento(2, 0);
        
        // Assert
        assertEquals(novaTranca.getStatus(), StatusTranca.OCUPADA);
        assertEquals(novaTranca.getBicicleta(), 0);

    }

    @Test
    void testRealizaTrancamentoTrancaQuandoBicicletaExiste() {
        // Act
        when(bicicletaService.alteraStatusBicicleta(1, StatusBicicleta.DISPONIVEL)).thenReturn(bicicletaMock);
        Tranca novaTranca = trancaService.realizarTrancamento(1, 1);

        // Assert
        assertEquals(novaTranca.getStatus(), StatusTranca.OCUPADA);
        assertEquals(novaTranca.getBicicleta(), 1);
        verify(bicicletaService).alteraStatusBicicleta(1, StatusBicicleta.DISPONIVEL);
    }
    

    @Test
    void testRealizaDestrancamentoTrancaQuandoBicicletaNaoExiste() {
        // Act
        Tranca novaTranca = trancaService.realizarDestrancamento(2, 0);
        
        // Assert
        assertEquals(novaTranca.getStatus(), StatusTranca.LIVRE);
        assertEquals(novaTranca.getBicicleta(), 0);
    }

    @Test
    void testRealizaDestrancamentoTrancaQuandoBicicletaExiste() {
        // Act
        when(bicicletaService.alteraStatusBicicleta(1, StatusBicicleta.EM_USO)).thenReturn(bicicletaMock);
        Tranca novaTranca = trancaService.realizarDestrancamento(1, 1);

        // Assert
        assertEquals(novaTranca.getStatus(), StatusTranca.LIVRE);
        assertEquals(novaTranca.getBicicleta(), 0);
        verify(bicicletaService).alteraStatusBicicleta(1, StatusBicicleta.EM_USO);
    }
}
