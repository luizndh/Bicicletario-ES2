package com.equipamento.servico;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.Tranca;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TrancaServiceTest {
    static Tranca t1 = new Tranca(new TrancaDTO(1, "", "", "", "LIVRE"));;
    static Tranca t2 = new Tranca(new TrancaDTO(2, "", "", "", "LIVRE"));

    @InjectMocks
    TrancaService trancaService;

    @BeforeAll
    public static void setUp() {
        Tranca.trancas.add(t1);
        Tranca.trancas.add(t2);
    }

    @Test
    public void naoEncontraTranca() {
        assertThrows(IllegalArgumentException.class, () ->  trancaService.recuperaTrancaPorId(2));
    }

    @Test
    public void encontraTranca() {
        assertEquals(Tranca.trancas.get(0), t1);
    }
}
