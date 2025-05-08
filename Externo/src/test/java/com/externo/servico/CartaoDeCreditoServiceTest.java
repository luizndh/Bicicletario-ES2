package com.externo.servico;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.externo.dto.CartaoDeCreditoDTO;

@ExtendWith(MockitoExtension.class)
public class CartaoDeCreditoServiceTest {

    @InjectMocks
    private CartaoDeCreditoService cartaoDeCreditoService;


    @Test
    void testValidaCartaoDeCreditoNumeroInvalido() {
        CartaoDeCreditoDTO dto1 = new CartaoDeCreditoDTO("Luiz Inacio", "102936574391", "09/2029","857");
        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.validaCartaoDeCredito(dto1));
    }

    @Test
    void testValidaCartaoDeCreditoDataInvalida() {
        CartaoDeCreditoDTO dto2 = new CartaoDeCreditoDTO("Jose das Neves", "9372548102937461", "055/2026", "175");
        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.validaCartaoDeCredito(dto2));
    }

    @Test
    void testValidaCartaoDeCreditoCvvInvalido() {
        CartaoDeCreditoDTO dto3 = new CartaoDeCreditoDTO("Fernando Barbosa", "8172637461098627", "01/2024", "0384");
        assertThrows(IllegalArgumentException.class, () -> cartaoDeCreditoService.validaCartaoDeCredito(dto3));
    }

    @Test
    void testValidaCartaoDeCreditoCartaoValido() {
        CartaoDeCreditoDTO dto4 = new CartaoDeCreditoDTO("Joao Silva", "7042654798098364", "08/2027", "038");
        assertDoesNotThrow(() -> cartaoDeCreditoService.validaCartaoDeCredito(dto4));
    }
}