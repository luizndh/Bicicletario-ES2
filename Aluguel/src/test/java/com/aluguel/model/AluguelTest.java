package com.aluguel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.aluguel.dto.AluguelDTO;
import com.aluguel.dto.NovoAluguelDTO;

public class AluguelTest {

    private Aluguel aluguel;
    List<Aluguel> alugueis;

    @BeforeEach
    public void setUp() {
        aluguel = new Aluguel(new NovoAluguelDTO("1", "2"));
        aluguel.atualizaAluguel(new AluguelDTO("1", "10:00", "2", "11:00", "10", "3", "4"));

        alugueis = new ArrayList<>();
        alugueis.add(aluguel);
    }

    @Test
    void testGetBicicleta() {
        assertEquals(1, aluguel.getBicicleta());
    }

    @Test
    void testGetHoraInicio() {
        assertEquals("10:00", aluguel.getHoraInicio());
    }

    @Test
    void testGetTrancaFim() {
        assertEquals(2, aluguel.getTrancaFim());
    }

    @Test
    void testGetHoraFim() {
        assertEquals("11:00", aluguel.getHoraFim());
    }

    @Test
    void testGetCobranca() {
        assertEquals(10, aluguel.getCobranca());
    }

    @Test
    void testGetCiclista() {
        assertEquals(3, aluguel.getCiclista());
    }

    @Test
    void testGetTrancaInicio() {
        assertEquals(4, aluguel.getTrancaInicio());
    }

    @Test
    void testAtualizaAluguel() {
        AluguelDTO dadosAlteracaoAluguel = new AluguelDTO("1", "10:00", "2", "12:00", "20", "3", "4");
        aluguel.atualizaAluguel(dadosAlteracaoAluguel);
        assertEquals("12:00", aluguel.getHoraFim());
        assertEquals(20, aluguel.getCobranca());
    }

    @Test
    void testAlugueis() {
        assertEquals(1, alugueis.size());
        assertEquals(aluguel, alugueis.get(0));
    }
}
