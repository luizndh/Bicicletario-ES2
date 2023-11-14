package com.aluguel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.aluguel.DTO.AluguelDTO;

public class AluguelTest {

    private Aluguel aluguel;

    @BeforeEach
    public void setUp() {
        aluguel = new Aluguel(new AluguelDTO(1, "10:00", 2, "11:00", 10, 3, 4));
        Aluguel.alugueis.add(aluguel);
    }

    @Test
    public void testGetBicicleta() {
        assertEquals(1, aluguel.getBicicleta());
    }

    @Test
    public void testGetHoraInicio() {
        assertEquals("10:00", aluguel.getHoraInicio());
    }

    @Test
    public void testGetTrancaFim() {
        assertEquals(2, aluguel.getTrancaFim());
    }

    @Test
    public void testGetHoraFim() {
        assertEquals("11:00", aluguel.getHoraFim());
    }

    @Test
    public void testGetCobranca() {
        assertEquals(10, aluguel.getCobranca());
    }

    @Test
    public void testGetCiclista() {
        assertEquals(3, aluguel.getCiclista());
    }

    @Test
    public void testGetTrancaInicio() {
        assertEquals(4, aluguel.getTrancaInicio());
    }

    @Test
    public void testAtualizaAluguel() {
        AluguelDTO dadosAlteracaoAluguel = new AluguelDTO(1, "10:00", 2, "12:00", 20, 3, 4);
        aluguel.atualizaAluguel(dadosAlteracaoAluguel);
        assertEquals("12:00", aluguel.getHoraFim());
        assertEquals(20, aluguel.getCobranca());
    }

    @Test
    public void testAlugueis() {
        List<Aluguel> alugueis = Aluguel.alugueis;
        assertEquals(1, alugueis.size());
        assertEquals(aluguel, alugueis.get(0));
    }
}
