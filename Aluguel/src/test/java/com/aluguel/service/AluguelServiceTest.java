package com.aluguel.service;

import com.aluguel.Integracoes;
import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.CobrancaDTO;
import com.aluguel.dto.NovaCobrancaDTO;
import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.dto.NovoCiclistaDTO;
import com.aluguel.dto.TrancaDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.Ciclista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AluguelServiceTest {

    @Mock
    private Integracoes integracoes;

    @Mock
    private CiclistaService ciclistaService;

    @InjectMocks
    private AluguelService aluguelService;

    @Mock
    private AluguelService aluguelServiceMock;

    @Mock
    private Aluguel aluguel;

    @Mock
    private List<Aluguel> alugueis;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Arrange
        NovoAluguelDTO aluguelDTO1 = new NovoAluguelDTO("1", "2");
        NovoAluguelDTO aluguelDTO2 = new NovoAluguelDTO("2", "3");
        NovoAluguelDTO aluguelDTO3 = new NovoAluguelDTO("3", "4");

        alugueis = new ArrayList<Aluguel>();
        alugueis.add(new Aluguel(aluguelDTO1));
        alugueis.add(new Aluguel(aluguelDTO2));
        alugueis.add(new Aluguel(aluguelDTO3));
    }

    @Test
    void testRecuperaAlugueis() {
        // Arrange
        when(aluguelServiceMock.recuperaAlugueis()).thenReturn(alugueis);

        // Act
        List<Aluguel> alugueisRetornados = aluguelServiceMock.recuperaAlugueis();

        // Assert
        assertEquals(alugueis, alugueisRetornados);
    }

    @Test
    void testRegistraAluguel() {
        // Arrange
        String ciclista = "1";
        String trancaInicio = "1";

        NovoAluguelDTO dadosCadastroAluguel = new NovoAluguelDTO(ciclista, trancaInicio);

        when(ciclistaService.recuperaCiclistaPorId(anyInt())).thenReturn(new Ciclista(new NovoCiclistaDTO()));
        when(ciclistaService.verificaSeCiclistaPodeAlugar(anyInt())).thenReturn(true);
        when(integracoes.recuperaTrancaPorId(anyInt())).thenReturn(new TrancaDTO(0, 0, 0, trancaInicio, trancaInicio, ciclista, trancaInicio));
        when(integracoes.recuperaBicicletaPorId(anyInt())).thenReturn(new BicicletaDTO("0", "marca", "modelo", "ano", "numero", "status"));
        when(integracoes.realizaCobranca(any(NovaCobrancaDTO.class))).thenReturn(new CobrancaDTO("status", "horaSolicitacao", "horaFinalizacao", "valor", "ciclista"));
        when(integracoes.destrancaTranca(anyInt(), anyInt())).thenReturn(true);
        when(integracoes.enviaEmail(anyString(), anyString(), anyString())).thenReturn(true);

        // Act
        Aluguel actualAluguel = aluguelService.registraAluguel(dadosCadastroAluguel);

        // Assert
        assertTrue(Aluguel.alugueis.contains(actualAluguel));
    }

    @Test
    void testRecuperaAluguelPorIdBicicleta() {
        // Arrange
        int idBicicleta = 1;

        when(aluguelServiceMock.recuperaAluguelPorIdBicicleta(idBicicleta)).then(invocation -> {
            Aluguel aluguel = new Aluguel(new NovoAluguelDTO("1", "2"));
            aluguel.atualizaAluguel(new AluguelDTO("0", "10:00", "2", "11:00", "10", "3", "4"));
            return aluguel;
        });

        // Act
        Aluguel actualAluguel = aluguelServiceMock.recuperaAluguelPorIdBicicleta(idBicicleta);

        // Assert
        assertEquals(alugueis.get(0).getBicicleta(), actualAluguel.getBicicleta());
    }
}