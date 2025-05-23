package com.externo.dto;

import com.externo.model.Cobranca;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class CobrancaDTOTest {
    @Test
    public void testCiclistaDTO() {
        // Arrange
        Cobranca.StatusCobranca status = Cobranca.StatusCobranca.PENDENTE;
        String horaSolicitacao = "01/01/2023 00:00:00";
        String horaFinalizacao = "01/01/2023 00:19:30";
        long valor = 322L;
        int ciclista = 1;

        // Act
        CobrancaDTO cobrancaDTO = new CobrancaDTO(status.toString(), horaSolicitacao, horaFinalizacao, valor, ciclista);

        // Assert
        assertNotNull(cobrancaDTO);
        assertEquals(status.toString(), cobrancaDTO.status());
        assertEquals(horaSolicitacao, cobrancaDTO.horaSolicitacao());
        assertEquals(horaFinalizacao, cobrancaDTO.horaFinalizacao());
        assertEquals(valor, cobrancaDTO.valor());
        assertEquals(ciclista, cobrancaDTO.ciclista());
    }
}