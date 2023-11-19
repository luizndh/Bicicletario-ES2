package com.externo.servico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.servico.CartaoDeCreditoService;

import static org.junit.jupiter.api.Assertions.*;

//TODO testar, mas a principio esta OK
@ExtendWith(MockitoExtension.class)
public class CartaoDeCreditoServiceTest {

    @Mock
    private CartaoDeCreditoService cartaoDeCreditoService;

    @Mock
    private List<CartaoDeCreditoDTO> cartoesDeCreditos;

    @BeforeEach
    void setUp() {
        // Arrange
        CartaoDeCreditoDTO dto1 = new CartaoDeCreditoDTO("Luiz Inacio", "102936574391", "09/2029","857");
        CartaoDeCreditoDTO dto2 = new CartaoDeCreditoDTO("Jose das Neves", "9372548102937461", "05/20066", "175");
        CartaoDeCreditoDTO dto3 = new CartaoDeCreditoDTO("Fernando Barbosa", "8172637461098627", "01/2024", "0384");
        CartaoDeCreditoDTO dto4 = new CartaoDeCreditoDTO("Joao Silva", "7042654798098364", "08/2027", "038");

        cartoesDeCreditos.add(dto1);
        cartoesDeCreditos.add(dto2);
        cartoesDeCreditos.add(dto3);
        cartoesDeCreditos.add(dto4);
    }

    @Test
    void testValidaCartaoDeCreditoNumeroInvalido() {
        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.validaCartaoDeCredito(cartoesDeCreditos.get(1)));
    }

    @Test
    void testValidaCartaoDeCreditoDataInvalida() {
        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.validaCartaoDeCredito(cartoesDeCreditos.get(2)));
    }

    @Test
    void testValidaCartaoDeCreditoCvvInvalido() {
        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.validaCartaoDeCredito(cartoesDeCreditos.get(3)));
    }

    @Test
    void testValidaCartaoDeCreditoCartaoValido() {
        assertDoesNotThrow(() -> cartaoDeCreditoService.validaCartaoDeCredito(cartoesDeCreditos.get(4)));
    }
}