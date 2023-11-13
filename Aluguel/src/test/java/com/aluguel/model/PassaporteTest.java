package com.aluguel.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PassaporteTest {
    
    @Test
    public void testGetNumero() {
        Passaporte passaporte = new Passaporte("123456", "01/01/2025", "Brasil");
        assertEquals("123456", passaporte.getNumero());
    }
    
    @Test
    public void testGetValidade() {
        Passaporte passaporte = new Passaporte("123456", "01/01/2025", "Brasil");
        assertEquals("01/01/2025", passaporte.getValidade());
    }
    
    @Test
    public void testGetPais() {
        Passaporte passaporte = new Passaporte("123456", "01/01/2025", "Brasil");
        assertEquals("Brasil", passaporte.getPais());
    }
}
