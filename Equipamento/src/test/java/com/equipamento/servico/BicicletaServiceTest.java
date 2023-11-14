package com.equipamento.servico;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.model.Bicicleta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BicicletaServiceTest {

    @InjectMocks
    BicicletaService bicicletaService;

    @Mock
    public List<Bicicleta> bicicletas;

    @Mock
    Bicicleta bicicleta;

    @BeforeEach
    void setUp() {
        BicicletaDTO bicicletaDTO1 = new BicicletaDTO("narca teste1", "modelo teste1", "2021", 1, "NOVA");
        BicicletaDTO bicicletaDTO2 = new BicicletaDTO("marca teste2", "modelo teste2", "2022", 2, "EM_USO");
        BicicletaDTO bicicletaDTO3 = new BicicletaDTO("marca teste3", "modelo teste3", "2023", 3, "NOVA");

        bicicletas = new ArrayList<>();
        bicicleta = new Bicicleta(bicicletaDTO1);
        bicicletas.add(bicicleta);
        bicicletas.add(new Bicicleta(bicicletaDTO2));
        bicicletas.add(new Bicicleta(bicicletaDTO3));


    }

    @Test
    void testRecuperaBicicletaPorIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> bicicletaService.recuperaBicicletaPorId(-1));
    }

    @Test
    void testRecuperaBicicletaPorIdQueNaoExiste() {
        assertThrows(NoSuchElementException.class, () -> bicicletaService.recuperaBicicletaPorId(2));
    }

    /*
    @Test
    public void testAlteraStatus() {
        // Arrange
        when(bicicletaService.recuperaBicicletaPorId(1)).thenReturn(bicicleta);

        // Act
        Bicicleta b = bicicletaService.alteraStatusBicicleta(bicicleta.getId(), Bicicleta.StatusBicicleta.EM_USO);

        // Assert
        assertEquals(Bicicleta.StatusBicicleta.EM_USO, b.getStatus());
    }
    */


}
