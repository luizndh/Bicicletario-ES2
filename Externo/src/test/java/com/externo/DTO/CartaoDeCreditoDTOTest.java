package com.externo.DTO;

import org.junit.jupiter.api.Test;

import com.externo.dto.CartaoDeCreditoDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartaoDeCreditoDTOTest {
//TODO PRONTO
    @Test
    public void testConstructor() {
        // Arrange
        String nomeTitular = "Luis Fumado";
        String numero = "2345678";
        String validade = "12/25";
        String cvv = "123";

        // Act
        CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO(nomeTitular, numero, validade, cvv);

        // Assert
        assertEquals(nomeTitular, cartao.nomeTitular());
        assertEquals(numero, cartao.numero());
        assertEquals(validade, cartao.validade());
        assertEquals(cvv, cartao.cvv());
    }
}
