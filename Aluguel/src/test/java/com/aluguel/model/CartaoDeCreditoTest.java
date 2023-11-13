package com.aluguel.model;

import com.aluguel.DTO.CartaoDeCreditoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartaoDeCreditoTest {

    private CartaoDeCredito cartaoDeCredito;

    @BeforeEach
    public void setUp() {
        CartaoDeCreditoDTO cartaoDeCreditoDTO = new CartaoDeCreditoDTO("Luis Fumado","2345678","12/25","123");
        cartaoDeCredito = new CartaoDeCredito(cartaoDeCreditoDTO);
    }

    @Test
    public void testGetId() {
        assertEquals(CartaoDeCredito.cartoesDeCreditos.size()+1, cartaoDeCredito.getId());
    }

    @Test
    public void testGetNomeTitular() {
        assertEquals("Luis Fumado", cartaoDeCredito.getNomeTitular());
    }

    @Test
    public void testGetNumero() {
        assertEquals("2345678", cartaoDeCredito.getNumero());
    }

    @Test
    public void testGetValidade() {
        assertEquals("12/25", cartaoDeCredito.getValidade());
    }

    @Test
    public void testGetCvv() {
        assertEquals("123", cartaoDeCredito.getCvv());
    }

    @Test
    public void testAtualizaCartaoDeCredito() {
        CartaoDeCreditoDTO cartaoDeCreditoDTO = new CartaoDeCreditoDTO("Luisa Fumada","8765432","01/30","456");
        cartaoDeCredito.atualizaCartaoDeCredito(cartaoDeCreditoDTO);
        assertEquals("Luisa Fumada", cartaoDeCredito.getNomeTitular());
        assertEquals("8765432", cartaoDeCredito.getNumero());
        assertEquals("01/30", cartaoDeCredito.getValidade());
        assertEquals("456", cartaoDeCredito.getCvv());
    }
}
