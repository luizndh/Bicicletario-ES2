package com.externo.DTO;

import com.externo.model.Cobranca;
import com.externo.DTO.CobrancaDTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//TODO PRONTO
public class CobrancaDTOTest {

    @Test
    public void testCiclistaDTO() {
        // Arrange
        Cobranca.StatusCobranca status = Cobranca.StatusCobranca.PENDENTE;
        String horaSolicitacao = "01/01/2023 00:00:00";
        String horaFinalizacao = "01/01/2023 00:19:30";
        float valor = 3.22f;
        int ciclista = 1;

        // Act
        CobrancaDTO cobrancaDTO = new CobrancaDTO(status, horaSolicitacao, horaFinalizacao, valor, ciclista);

        // Assert
        assertNotNull(cobrancaDTO);
        assertEquals(status, cobrancaDTO.status());
        assertEquals(horaSolicitacao, cobrancaDTO.horaSolicitacao());
        assertEquals(horaFinalizacao, cobrancaDTO.horaFinalizacao());
        assertEquals(valor, cobrancaDTO.valor());
        assertEquals(ciclista, cobrancaDTO.ciclista());
    }
}