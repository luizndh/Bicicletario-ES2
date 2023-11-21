package com.externo.servico;

import com.externo.dto.CartaoDeCreditoDTO;
import com.externo.model.Cobranca;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.NoSuchElementException;

import com.externo.dto.CobrancaDTO;
import com.externo.servico.CobrancaService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CobrancaServiceTest {
    @Mock
    private CobrancaService cobrancaService;

    @Mock
    private List<Cobranca> cobrancas;

    @BeforeEach
    void setUp() {
        // Arrange
        CobrancaDTO dto1 = new CobrancaDTO(Cobranca.StatusCobranca.PAGA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 10.0f, 1);
        CobrancaDTO dto2 = new CobrancaDTO(Cobranca.StatusCobranca.FALHA.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 10.0f, 2);
        CobrancaDTO dto3 = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 10.0f, 3);
        CobrancaDTO dto4 = new CobrancaDTO(Cobranca.StatusCobranca.PENDENTE.toString(), "2021-05-01T12:00:00", "2021-05-01T12:00:00", 10.0f, 4);

        cobrancas.add(new Cobranca(dto1));
        cobrancas.add(new Cobranca(dto2));
        cobrancas.add(new Cobranca(dto3));
        cobrancas.add(new Cobranca(dto4));
    }

    //@Test
    //void testObterCobrancaInvalido() {
    //    assertThrows(IllegalArgumentException.class, () -> cobrancaService.obterCobranca(-1));
    //}

    //@Test
    //void testObterCobrancaQueNaoExiste() {
    //    assertThrows(NoSuchElementException.class, () -> cobrancaService.obterCobranca(5));
    //}

    // @Test
    // void testObterCobranca() {
    //     // Act
    //     Cobranca cobranca = cobrancaService.obterCobranca(1);

    //     // Assert
    //     assertNotNull(cobranca);
    //     assertEquals(cobranca.getId(), 1);
    //     assertEquals(cobranca.getStatus(), Cobranca.StatusCobranca.PAGA);
    // }

    //TODO adicionar o resto dos testes:
    // - testar se a cobranca foi adicionada na fila
    // - testar se a cobranca foi processada e aprovada
    // - testar se a cobranca foi processada e reprovada


}
