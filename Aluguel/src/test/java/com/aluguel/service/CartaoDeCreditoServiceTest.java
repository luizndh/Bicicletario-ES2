package com.aluguel.service;

import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartaoDeCreditoServiceTest {

    @Mock
    private CartaoDeCreditoService cartaoDeCreditoService;

    @Mock
    private List<CartaoDeCredito> cartoesDeCreditos;

    @BeforeEach
    void setUp() {
        // Arrange
        CartaoDeCreditoDTO cartaoDeCreditoDTO1 = new CartaoDeCreditoDTO("Joao Silva", "5342663286475435", "01/22","411");
        CartaoDeCreditoDTO cartaoDeCreditoDTO2 = new CartaoDeCreditoDTO("Lucas Sacul", "1234567890123456", "12/23", "123");
        CartaoDeCreditoDTO cartaoDeCreditoDTO3 = new CartaoDeCreditoDTO("Lulu Santos", "4444222233331111", "07/28", "146");

        cartoesDeCreditos = new ArrayList<CartaoDeCredito>();
        cartoesDeCreditos.add(new CartaoDeCredito(cartaoDeCreditoDTO1));
        cartoesDeCreditos.add(new CartaoDeCredito(cartaoDeCreditoDTO2));
        cartoesDeCreditos.add(new CartaoDeCredito(cartaoDeCreditoDTO3));
    }

    @Test
    void testRecuperaCartaoDeCreditoPorId() {
        // Arrange
        int idCiclista = 1;
        when(cartaoDeCreditoService.recuperaCartaoDeCreditoPorId(idCiclista)).then(invocation -> {
            CartaoDeCredito cartaoDeCredito = cartoesDeCreditos.get((idCiclista - 1));
            return cartaoDeCredito;
        });

        // Act
        CartaoDeCredito cartaoDeCredito = cartaoDeCreditoService.recuperaCartaoDeCreditoPorId(idCiclista);

        // Assert
        assertNotNull(cartaoDeCredito);
        assertEquals(1, cartaoDeCredito.getId());
        assertEquals("Joao Silva", cartaoDeCredito.getNomeTitular());
        assertEquals("5342663286475435", cartaoDeCredito.getNumero());
        assertEquals("01/22", cartaoDeCredito.getValidade());
        assertEquals("411", cartaoDeCredito.getCvv());
    }

    @Test
    void testRecuperaCartoesDeCreditos() {
        // Act
        List<CartaoDeCredito> cartoesDeCredito = cartaoDeCreditoService.recuperaCartoesDeCreditos();

        // Assert
        assertNotNull(cartoesDeCredito);
        assertEquals(CartaoDeCredito.cartoesDeCreditos.size(), cartoesDeCredito.size());
    }

    @Test
    void testAlteraCartaoDeCredito() {
        // Arrange
        int idCiclista = 1;
        CartaoDeCreditoDTO dadosAlteracaoCartaoDeCredito = new CartaoDeCreditoDTO("Lucas Sacul", "1234567890123456", "12/23", "123");
        when(cartaoDeCreditoService.alteraCartaoDeCredito(idCiclista, dadosAlteracaoCartaoDeCredito)).then(invocation -> {
            CartaoDeCredito cartaoDeCredito = cartoesDeCreditos.get((idCiclista - 1));
            cartaoDeCredito.atualizaCartaoDeCredito(dadosAlteracaoCartaoDeCredito);
            return cartaoDeCredito;
        });

        // Act
        CartaoDeCredito cartaoDeCredito = cartaoDeCreditoService.alteraCartaoDeCredito(idCiclista, dadosAlteracaoCartaoDeCredito);

        // Assert
        assertNotNull(cartaoDeCredito);
        assertEquals(idCiclista, cartaoDeCredito.getId());
        assertEquals("Lucas Sacul", cartaoDeCredito.getNomeTitular());
        assertEquals("1234567890123456", cartaoDeCredito.getNumero());
        assertEquals("12/23", cartaoDeCredito.getValidade());
        assertEquals("123", cartaoDeCredito.getCvv());
    }
}