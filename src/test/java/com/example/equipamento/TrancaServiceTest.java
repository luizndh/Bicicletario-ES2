package com.example.equipamento;

import com.example.equipamento.model.Tranca;
import com.example.equipamento.service.TrancaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TrancaServiceTest {

    @Mock
    TrancaService trancaService;
    @Test
    public void naoEncontraTranca() {
        // Dado
        List<Tranca> lista = new ArrayList<>();
        lista.add(new Tranca(2));
        lista.add(new Tranca(5));

        // Quando
        Tranca t = trancaService.getTrancaPorId(7);

        // Então
        Assertions.assertNull(t);
    }
}
