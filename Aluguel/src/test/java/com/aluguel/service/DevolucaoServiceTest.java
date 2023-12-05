package com.aluguel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aluguel.Integracoes;
import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.dto.NovaDevolucaoDTO;
import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.dto.NovoCiclistaDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.model.Ciclista;
import com.aluguel.model.Devolucao;

@ExtendWith(MockitoExtension.class)
public class DevolucaoServiceTest {

    @Mock
    Integracoes integracoes;

    @Mock
    CiclistaService ciclistaService;

    @Mock
    CartaoDeCreditoService cartaoDeCreditoService;

    @InjectMocks
    DevolucaoService devolucaoService;

    @Mock
    AluguelService aluguelService;
    
    @Test
    public void testRegistraDevolucao() {
        // Arrange
        String idTranca = "123";
        String idBicicleta = "456";

        when(integracoes.recuperaBicicletaPorId(Integer.parseInt(idBicicleta))).thenReturn(null);
        when(integracoes.trancaTranca(anyInt(), anyInt())).thenReturn(true);
        when(integracoes.enviaEmail(anyString(), anyString(), anyString())).thenReturn(true);
        when(ciclistaService.recuperaCiclistaPorId(anyInt())).thenReturn(new Ciclista(new NovoCiclistaDTO()));
        when(cartaoDeCreditoService.recuperaCartaoDeCreditoPorId(anyInt())).thenReturn(new CartaoDeCredito(new CartaoDeCreditoDTO("Jofrey", "2345678", "10/22", "123")));
        when(aluguelService.recuperaAluguelPorIdBicicleta(Integer.parseInt(idBicicleta))).then(invocation -> {
            Aluguel aluguel = new Aluguel(new NovoAluguelDTO("1", "2"));
            aluguel.atualizaAluguel(new AluguelDTO("1", "10:00", "2", "11:00", "10", "3", "4", "EM_ANDAMENTO"));
            return aluguel;
        });
        
        // Act
        Devolucao devolucao = devolucaoService.registraDevolucao(new NovaDevolucaoDTO(idBicicleta, idTranca));
        
        // Assert
        assertTrue(Devolucao.devolucoes.contains(devolucao));
        assertEquals(Integer.parseInt(idBicicleta), devolucao.getBicicleta());
        assertEquals(Integer.parseInt(idTranca), devolucao.getTrancaFim());
    }
}