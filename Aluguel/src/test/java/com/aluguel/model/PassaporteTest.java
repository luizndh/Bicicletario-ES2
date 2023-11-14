package com.aluguel.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PassaporteTest {
    
    @Test
    void testGetNumero() {
        Passaporte passaporte = new Passaporte("123456", "01/01/2025", "Brasil");
        assertEquals("123456", passaporte.getNumero());
    }
    
    @Test
    void testGetValidade() {
        Passaporte passaporte = new Passaporte("123456", "01/01/2025", "Brasil");
        assertEquals("01/01/2025", passaporte.getValidade());
    }
    
    @Test
    void testGetPais() {
        Passaporte passaporte = new Passaporte("123456", "01/01/2025", "Brasil");
        assertEquals("Brasil", passaporte.getPais());
    }
}
