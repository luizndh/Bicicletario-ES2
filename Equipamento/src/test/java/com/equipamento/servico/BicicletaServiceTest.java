package com.equipamento.servico;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.InclusaoBicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Bicicleta.StatusBicicleta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.equipamento.model.Bicicleta.bicicletas;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BicicletaServiceTest {

    @Spy
    @InjectMocks
    BicicletaService bicicletaService;
    
    Bicicleta bicicleta;

    @Mock
    Bicicleta bicicletaMock;

    @Spy
    Bicicleta bicicletaSpy;

    @BeforeEach
    void setUp() {
        BicicletaDTO bicicletaDTO1 = new BicicletaDTO("narca teste1", "modelo teste1", "2021", 1, "NOVA");
        BicicletaDTO bicicletaDTO2 = new BicicletaDTO("marca teste2", "modelo teste2", "2022", 2, "EM_USO");
        BicicletaDTO bicicletaDTO3 = new BicicletaDTO("marca teste3", "modelo teste3", "2023", 3, "NOVA");

        Bicicleta.bicicletas = new ArrayList<>();
        bicicleta = new Bicicleta(bicicletaDTO2);

        Bicicleta.bicicletas.add(bicicleta);
        Bicicleta.bicicletas.add(new Bicicleta(bicicletaDTO1));
        Bicicleta.bicicletas.add(new Bicicleta(bicicletaDTO3));
    }

    @Test
    void testRecuperaBicicletaPorIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> bicicletaService.recuperaBicicletaPorId(-1));
    }

    @Test
    void testRecuperaBicicletaPorIdQueNaoExiste() {
        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> bicicletaService.recuperaBicicletaPorId(10));
    }

    @Test
    void testRecuperaBicicletaPorId() {
        // Act
        Bicicleta bicicleta = bicicletaService.recuperaBicicletaPorId(1);

        // Assert
        assertNotNull(bicicleta);
        assertEquals(bicicleta.getId(), 1);
        assertEquals(bicicleta.getStatus(), StatusBicicleta.EM_USO);
        assertEquals(Bicicleta.bicicletas.get(0), bicicleta);
    }

    @Test
    void testAlteraStatus() {
        // Arrange
        when(bicicletaService.recuperaBicicletaPorId(2)).thenReturn(bicicleta);
        assertEquals(bicicleta.getStatus(), StatusBicicleta.EM_USO);

        // Act
        Bicicleta b = bicicletaService.alteraStatusBicicleta(2, "REPARO_SOLICITADO");

        // Assert
        assertEquals(b.getStatus(), StatusBicicleta.REPARO_SOLICITADO);
    }
    
    @Test
    void testIntegraNaRedeQuandoStatusEmUso() {
        // Arrange
        when(bicicletaMock.getStatus()).thenReturn(StatusBicicleta.EM_USO);
        when(bicicletaService.recuperaBicicletaPorId(2)).thenReturn(bicicletaMock);

        // Act
        bicicletaService.integrarNaRede(new InclusaoBicicletaDTO(1, 2, 1));

        // Assert
        verify(bicicletaMock, times(0)).setStatus(StatusBicicleta.DISPONIVEL);
        verify(bicicletaMock, times(0)).adicionaRegistroNoHistoricoDeInclusao(any(InclusaoBicicletaDTO.class));
    }

    @Test
    void testIntegraNaRedeQuandoStatusDiferenteDeEmUso() {
        // Arrange
        when(bicicletaMock.getStatus()).thenReturn(StatusBicicleta.NOVA);
        when(bicicletaService.recuperaBicicletaPorId(1)).thenReturn(bicicletaMock);

        // Act
        bicicletaService.integrarNaRede(new InclusaoBicicletaDTO(1, 1, 1));

        // Assert
        verify(bicicletaMock, times(1)).setStatus(StatusBicicleta.DISPONIVEL);
        verify(bicicletaMock, times(1)).adicionaRegistroNoHistoricoDeInclusao(any(InclusaoBicicletaDTO.class));
    }

    @Test
    void testRecuperaBicicletasArray() {
        // Act
        List<Bicicleta> lista = bicicletaService.recuperaBicicletas();

        // Assert
        assertEquals(lista.size(), 3);
        assertEquals(lista.get(0), bicicleta);
    }

    @Test
    void testCadastroBicicleta() {
        // Act
        BicicletaDTO bicicletaDTO = new BicicletaDTO("narca teste1", "modelo teste1", "2021", 1, "NOVA");
        Bicicleta b = bicicletaService.cadastraBicicleta(bicicletaDTO);

        // Assert
        assertEquals(bicicletas.size(), 4);
        assertEquals(bicicletas.get(3), b);
        assertEquals(bicicletas.get(3).getStatus(), StatusBicicleta.NOVA);
    }

    @Test
    void testAlteraBicicleta() {
        // Arrange
        BicicletaDTO dadosAlteracao = new BicicletaDTO("narca teste1", "modelo teste1", "2021", 1, "NOVA");
        when(bicicletaService.recuperaBicicletaPorId(1)).thenReturn(bicicletaMock);
        
        // Act
        bicicletaService.alteraBicicleta(1, dadosAlteracao);

        // Assert
        verify(bicicletaMock).atualizaBicicleta(dadosAlteracao);
    }
}
