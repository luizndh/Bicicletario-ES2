package com.aluguel.DTO;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartaoDeCreditoDTOTest {

    @Test
    public void testConstructor() {
        String nomeTitular = "Luis Fumado";
        String numero = "2345678";
        String validade = "12/25";
        String cvv = "123";

        CartaoDeCreditoDTO cartao = new CartaoDeCreditoDTO(nomeTitular, numero, validade, cvv);

        assertEquals(nomeTitular, cartao.nomeTitular());
        assertEquals(numero, cartao.numero());
        assertEquals(validade, cartao.validade());
        assertEquals(cvv, cartao.cvv());
    }
}
