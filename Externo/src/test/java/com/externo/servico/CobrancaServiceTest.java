package com.externo.servico;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.model.Cobranca;
import com.stripe.exception.StripeException;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.externo.dto.CobrancaDTO;
import com.externo.servico.CobrancaService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CobrancaServiceTest {
    @InjectMocks
    private CobrancaService cobrancaService;

    @InjectMocks
    private CobrancaService cobrancaService2;

    @BeforeEach
    void setUp() {
        // Arrange
        CobrancaDTO dto1 = new CobrancaDTO(Cobranca.StatusCobranca.PAGA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 1000L, 1);
        CobrancaDTO dto2 = new CobrancaDTO(Cobranca.StatusCobranca.FALHA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 2000L, 2);
        CobrancaDTO dto3 = new CobrancaDTO(Cobranca.StatusCobranca.PAGA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 3000L, 3);
        CobrancaDTO dto4 = new CobrancaDTO(Cobranca.StatusCobranca.PAGA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 4000L, 4);

        cobrancaService.incluiFilaCobranca(dto1);
        cobrancaService.incluiFilaCobranca(dto2);
        cobrancaService.incluiFilaCobranca(dto3);
        cobrancaService.incluiFilaCobranca(dto4);
    }

    // /cobranca
    @Test
    void testRealizaCobrancaValido() throws StripeException {
        // Arrange
        CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO("4242424242424242", "123", "12/2028", "2021");
        CobrancaDTO cobranca = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 1000L, 1);

        // Act
        Cobranca result = cobrancaService.realizaCobranca(new Cobranca(cobranca));

        // Assert
        assertEquals(result.getId(), new Cobranca(cobranca).getId());
    }

    @Test
    void testRealizaCobrancaInvalido() throws StripeException {
        CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO("4242424242424242", "123", "12/2028", "2021");
        CobrancaDTO cobranca = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 1L, 1);

        assertThrows(IllegalArgumentException.class, () -> cobrancaService.realizaCobranca(new Cobranca(cobranca)));
    }

    // /processaCobrancasEmFila
    @Test
    void testProcessaCobrancasEmFilaValido() {
        // Arrange
        CobrancaDTO dto5 = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 4000L, 4);
        cobrancaService.incluiFilaCobranca(dto5);

        // Act
        List<Cobranca> result = cobrancaService.processaCobrancasEmFila();
        List<Cobranca> expected = new ArrayList<>();
        expected.add(new Cobranca(dto5));

        // Assert
        assertEquals(result.get(0).getId(), expected.get(0).getId());
    }

    @Test
    void testProcessaCobrancasEmFilaInvalido() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> cobrancaService.processaCobrancasEmFila());
    }

    // /filaCobranca
    @Test
    void testIncluiFilaCobrancaValido() {
        // Arrange
        CobrancaDTO cobranca = new CobrancaDTO(Cobranca.StatusCobranca.PAGA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 90000L, 1);

        // Act
        Cobranca result = cobrancaService.incluiFilaCobranca(cobranca);

        // Assert
        assertEquals(result.getId()+1, new Cobranca(cobranca).getId());
    }

    @Test
    void testIncluiFilaCobrancaInvalido() {
        // Arrange
        CobrancaDTO cobranca = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", -1L, 1);

        // Act
        assertThrows(IllegalArgumentException.class, () -> cobrancaService.incluiFilaCobranca(cobranca));
    }

    // /cobranca/{idCobranca}
    @Test
    void testObterCobrancaInvalido() {
        assertThrows(IllegalArgumentException.class, () -> cobrancaService.obterCobranca(-1));
    }

    @Test
    void testObterCobrancaQueNaoExiste() {
        assertThrows(IllegalArgumentException.class, () -> cobrancaService.obterCobranca(50));
    }

    @Test //!
    void testObterCobranca() {
        // Act
        Cobranca cobranca = cobrancaService.obterCobranca(1);

        // Assert
        assertNotNull(cobranca);
        assertEquals(cobranca.getId(), 1);
        assertEquals(Cobranca.StatusCobranca.PAGA, cobranca.getStatus());
    }
}
