package com.equipamento.servico;

import com.equipamento.model.Bicicleta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TrancaServiceTest {

    @InjectMocks
    BicicletaService bicicletaService;


    @Test
    void testRecuperaBicicletaPorIdInvalido() {
        assertThrows(IllegalArgumentException.class, () -> bicicletaService.recuperaBicicletaPorId(-1));
    }

    @Test
    void testRecuperaBicicletaPorIdQueNaoExiste() {
        assertThrows(NoSuchElementException.class, () -> bicicletaService.recuperaBicicletaPorId(2));
    }

    /*
    @BeforeEach
    public void setUp() {
        // Arrange

        TrancaDTO trancaDTO1 = new TrancaDTO(1, "localizacao teste1", "2021", "modelo teste1", "NOVA");
        TrancaDTO trancaDTO2 = new TrancaDTO(2, "localizacao teste2", "2022", "modelo teste2", "LIVRE");
        TrancaDTO trancaDTO3 = new TrancaDTO(3, "localizacao teste3", "2023", "modelo teste3", "APOSENTADA");

        trancas = new ArrayList<>();

        trancas.add(new Tranca(trancaDTO1));
        trancas.add(new Tranca(trancaDTO2));
        trancas.add(new Tranca(trancaDTO3));
    }


    @Test
    public void testRecuperaTrancaPorId() {
        // Arrange
        int id = trancas.get(0).getId();

        // Act + Assert
        Tranca tranca = this.trancaService.recuperaTrancaPorId(id);

        // Assert
        assertNotNull(tranca);
    }

*/
}
