package com.aluguel.model;

import com.aluguel.dto.CartaoDeCreditoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartaoDeCreditoTest {

    private CartaoDeCredito cartaoDeCredito;

    @BeforeEach
    void setUp() {
        CartaoDeCreditoDTO cartaoDeCreditoDTO = new CartaoDeCreditoDTO("Luis Fumado","2345678","12/25","123");
        cartaoDeCredito = new CartaoDeCredito(cartaoDeCreditoDTO);
    }

    @Test
    void testGetId() {
        assertEquals(CartaoDeCredito.cartoesDeCreditos.size()+1, cartaoDeCredito.getId());
    }

    @Test
    void testGetNomeTitular() {
        assertEquals("Luis Fumado", cartaoDeCredito.getNomeTitular());
    }

    @Test
    void testGetNumero() {
        assertEquals("2345678", cartaoDeCredito.getNumero());
    }

    @Test
    void testGetValidade() {
        assertEquals("12/25", cartaoDeCredito.getValidade());
    }

    @Test
    void testGetCvv() {
        assertEquals("123", cartaoDeCredito.getCvv());
    }

    @Test
    void testAtualizaCartaoDeCredito() {
        CartaoDeCreditoDTO cartaoDeCreditoDTO = new CartaoDeCreditoDTO("Luisa Fumada","8765432","01/30","456");
        cartaoDeCredito.atualizaCartaoDeCredito(cartaoDeCreditoDTO);
        assertEquals("Luisa Fumada", cartaoDeCredito.getNomeTitular());
        assertEquals("8765432", cartaoDeCredito.getNumero());
        assertEquals("01/30", cartaoDeCredito.getValidade());
        assertEquals("456", cartaoDeCredito.getCvv());
    }
}
