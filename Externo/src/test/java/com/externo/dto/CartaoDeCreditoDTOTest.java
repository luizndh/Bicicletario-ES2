package com.externo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartaoDeCreditoDTOTest {
    @Test
    public void testConstructor() {
        // Arrange
        String nomeTitular = "Joao da Silva";
        String numero = "1234567891234567";
        String validade = "10/26";
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
