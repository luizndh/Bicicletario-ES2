package com.aluguel.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.NovaDevolucaoDTO;
import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.Devolucao;

@ExtendWith(MockitoExtension.class)
public class DevolucaoServiceTest {

    @InjectMocks
    DevolucaoService devolucaoService;

    @Mock
    AluguelService aluguelService;

    @BeforeEach
    void setUp() {
        //devolucaoService = new DevolucaoService();
        //aluguelService = new AluguelService();
    }
    
    @Test
    public void testRegistraDevolucao() {
        // Arrange
        String idTranca = "123";
        String idBicicleta = "456";

        when(aluguelService.recuperaAluguelPorIdBicicleta(Integer.parseInt(idBicicleta))).then(invocation -> {
            Aluguel aluguel = new Aluguel(new NovoAluguelDTO("1", "2"));
            aluguel.atualizaAluguel(new AluguelDTO(1, "10:00", 2, "11:00", 10, 3, 4));
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