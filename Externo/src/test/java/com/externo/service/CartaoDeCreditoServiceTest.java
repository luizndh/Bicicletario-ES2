package com.aluguel.service;

import com.aluguel.DTO.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CartaoDeCreditoServiceTest {

    private CartaoDeCreditoService cartaoDeCreditoService;

    @BeforeEach
    public void setUp() {
        cartaoDeCreditoService = new CartaoDeCreditoService();
    }

    @Test
    public void testRecuperaCartaoDeCreditoPorId() {
        // Arrange
        int idCiclista = 1;

        // Act
        CartaoDeCredito cartaoDeCredito = cartaoDeCreditoService.recuperaCartaoDeCreditoPorId(idCiclista);

        // Assert
        assertNotNull(cartaoDeCredito);
        assertEquals(idCiclista, cartaoDeCredito.getId());
    }

    @Test
    public void testRecuperaCartoesDeCreditos() {
        // Act
        List<CartaoDeCredito> cartoesDeCredito = cartaoDeCreditoService.recuperaCartoesDeCreditos();

        // Assert
        assertNotNull(cartoesDeCredito);
        assertEquals(CartaoDeCredito.cartoesDeCreditos.size(), cartoesDeCredito.size());
    }

    @Test
    public void testAlteraCartaoDeCredito() {
        // Arrange
        int idCiclista = 1;
        CartaoDeCreditoDTO dadosAlteracaoCartaoDeCredito = new CartaoDeCreditoDTO("Lucas Sacul", "1234567890123456", "12/23", "123");

        // Act
        CartaoDeCredito cartaoDeCredito = cartaoDeCreditoService.alteraCartaoDeCredito(idCiclista, dadosAlteracaoCartaoDeCredito);

        // Assert
        assertNotNull(cartaoDeCredito);
        assertEquals(idCiclista, cartaoDeCredito.getId());
        assertEquals("1234567890123456", cartaoDeCredito.getNumero());
    }
}