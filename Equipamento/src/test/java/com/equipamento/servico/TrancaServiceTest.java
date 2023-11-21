package com.equipamento.servico;

import com.equipamento.dto.InclusaoTrancaDTO;
import com.equipamento.dto.RetiradaTrancaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.TrancaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Tranca;
import com.equipamento.model.Tranca.StatusTranca;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.equipamento.model.Tranca.trancas;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrancaServiceTest {

    @Spy
    @InjectMocks
    TrancaService trancaService;

    @Mock
    BicicletaService bicicletaService;

    @Mock
    Tranca trancaMock;

    Tranca tranca;

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

        trancas = new ArrayList<>();

        tranca = new Tranca(trancaDTO1);
        tranca.setBicicleta(2);

        trancas.add(tranca);
        trancas.add(new Tranca(trancaDTO2));
        trancas.add(new Tranca(trancaDTO3));
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
        assertEquals(tranca.getStatus(), StatusTranca.NOVA);
        assertEquals(tranca.getId(), 1);
    }

    @Test
    void testRecuperaTrancas() {
        // Act
        List<Tranca> trancas = trancaService.recuperaTrancas();

        // Assert
        assertNotNull(trancas);
        assertEquals(trancas.size(), 3);
    }

    @Test
    void testCadastraTranca() {
        // Arrange
        TrancaDTO trancaDTO = new TrancaDTO(4, "localizacao teste4", "2024", "modelo teste4", "LIVRE");

        // Act
        Tranca novaTranca = trancaService.cadastraTranca(trancaDTO);

        // Assert
        assertNotNull(novaTranca);
        assertEquals(novaTranca.getId(), 4);
        assertEquals(novaTranca.getStatus(), StatusTranca.LIVRE);
    }

    @Test
    void testCadastraTrancaDadosInvalidos() {
        // Arrange
        TrancaDTO trancaDTO = new TrancaDTO(6, "localizacao teste4", "2024", "modelo teste4", "a");

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> trancaService.cadastraTranca(trancaDTO));
    }

    @Test
    void testAlteraTranca() {
        // Arrange
        TrancaDTO trancaDTO = new TrancaDTO(1, "localizacao teste4", "2024", "modelo teste4", "OCUPADA");
        when(trancaService.recuperaTrancaPorId(1)).thenReturn(trancaMock);

        // Act
        trancaService.alteraTranca(1, trancaDTO);

        // Assert
        verify(trancaMock).atualizaTranca(trancaDTO);
    }

    @Test
    void testExcluiTranca() {
        // Arrange
        int size = trancas.size();

        // Act
        trancaService.excluiTranca(1);

        // Assert
        assertEquals(trancas.size(), size - 1);
    }

    @Test
    void testAlteraStatus() {
        // Arrange
        when(trancaService.recuperaTrancaPorId(tranca.getId())).thenReturn(tranca);
        assertEquals(tranca.getStatus(), StatusTranca.NOVA);

        // Act
        Tranca novaTranca = trancaService.alteraStatusTranca(tranca.getId(), "OCUPADA");

        // Assert
        assertEquals(novaTranca.getStatus(), StatusTranca.OCUPADA);
    }

    @Test
    void testRealizaTrancamentoTrancaQuandoNaoEhPassada() {
        // Arrange
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act
        trancaService.realizarTrancamento(2, 0);
        
        // Assert
        verify(trancaMock).setStatus(StatusTranca.OCUPADA);
        verify(trancaMock, times(0)).setBicicleta(anyInt());
    }

    @Test
    void testRealizaTrancamentoTrancaQuandoBicicletaEhPassada() {
        // Act
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act
        trancaService.realizarTrancamento(2, 1);

        // Assert
        verify(trancaMock).setStatus(StatusTranca.OCUPADA);
        verify(trancaMock).setBicicleta(1);
        verify(bicicletaService).alteraStatusBicicleta(1, "DISPONIVEL");
    }
    

    @Test
    void testRealizaDestrancamentoTrancaQuandoBicicletaNaoEhPasada() {
        // Arrange
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act
        trancaService.realizarDestrancamento(2, 0);
        
        // Assert
        verify(trancaMock).setStatus(StatusTranca.LIVRE);
        verify(trancaMock, times(0)).setBicicleta(anyInt());
    }

    @Test
    void testRealizaDestrancamentoTrancaQuandoBicicletaEhPassada() {
        // Arrange
        when(trancaService.recuperaTrancaPorId(1)).thenReturn(trancaMock);

        // Act
        trancaService.realizarDestrancamento(1, 1);

        // Assert
        verify(trancaMock).setStatus(StatusTranca.LIVRE);
        verify(trancaMock).setBicicleta(0);
        verify(bicicletaService).alteraStatusBicicleta(1, "EM_USO");
    }

    @Test
    void testIntegraTrancaNaRede() {
        // Arrange
        InclusaoTrancaDTO dto = new InclusaoTrancaDTO(1, 2, 1);
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act
        trancaService.integrarNaRede(dto);

        // Assert
        verify(trancaMock).setStatus(StatusTranca.LIVRE);
        verify(trancaMock).adicionaRegistroNoHistoricoDeInclusao(dto);
    }

    @Test
    void testRetiraDaRedeParaReparo() {
        // Arrange
        RetiradaTrancaDTO dto = new RetiradaTrancaDTO(1, 2, 1, "EM_REPARO");
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act
        trancaService.retirarDaRede(dto);

        // Assert
        verify(trancaMock).setStatus(StatusTranca.EM_REPARO);
        verify(trancaMock).adicionaRegistroNoHistoricoDeRetirada(dto);
    }

    @Test
    void testRetiradaDaRedeParaAposentar() {
        // Arrange
        RetiradaTrancaDTO dto = new RetiradaTrancaDTO(1, 2, 1, "APOSENTADA");
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act
        trancaService.retirarDaRede(dto);

        // Assert
        verify(trancaMock).setStatus(StatusTranca.APOSENTADA);
        verify(trancaMock).adicionaRegistroNoHistoricoDeRetirada(dto);
    }

    @Test
    void testRetiraDaRedeStatusInvalido() {
        // Arrange
        RetiradaTrancaDTO dto = new RetiradaTrancaDTO(1, 2, 1, "NOVA");
        when(trancaService.recuperaTrancaPorId(2)).thenReturn(trancaMock);

        // Act + Assert
        assertThrows(IllegalArgumentException.class, () -> trancaService.retirarDaRede(dto));
    }

}
