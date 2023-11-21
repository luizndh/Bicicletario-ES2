package com.equipamento.servico;

import java.util.ArrayList;
import java.util.List;

import com.equipamento.dto.TotemDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Totem;
import com.equipamento.model.Tranca;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.equipamento.model.Totem.totens;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TotemServiceTest {

    @Spy
    @InjectMocks
    TotemService totemService;

    @Mock
    BicicletaService bicicletaService;

    @Mock
    Totem totemMock;

    @Mock
    Tranca trancaMock1;

    @Mock
    Tranca trancaMock2;

    @Mock
    Tranca trancaMock3;

    @Mock
    Bicicleta bicicletaMock1;

    @Mock
    Bicicleta bicicletaMock2;

    @Mock
    Bicicleta bicicletaMock3;

    @BeforeAll
    static void setUp() {
        Totem totem1 = new Totem(new TotemDTO("localizacao teste1", "descricao teste1"));
        Totem totem2 = new Totem(new TotemDTO("localizacao teste2", "descricao teste2"));
        Totem totem3 = new Totem(new TotemDTO("localizacao teste3", "descricao teste3"));

        totens = new ArrayList<>();

        totens.add(totem1);
        totens.add(totem2);
        totens.add(totem3);
    }

    @Test
    void testExcluiTotem() {
        // Arrange
        int size = totens.size();

        // Act
        totemService.excluiTotem(1);

        // Assert
        assertEquals(size - 1, totens.size());
    }

    @Test
    void testAlteraTotem() {
        // Arrange
        TotemDTO totemDTO = new TotemDTO("localizacao teste", "descricao teste");
        when(totemService.recuperaTotemPorId(1)).thenReturn(totemMock);

        // Act
        totemService.alteraTotem(1, totemDTO);

        // Assert
        verify(totemService).atualizaTotem(totemMock, totemDTO);
    }

    @Test
    void testCadastraTotem() {
        // Arrange
        TotemDTO totemDTO = new TotemDTO("localizacao teste", "descricao teste");

        // Act
        Totem t = totemService.cadastraTotem(totemDTO);

        // Assert
        assertNotNull(t);
        assertEquals(t.getLocalizacao(), "localizacao teste");
        assertEquals(t.getDescricao(), "descricao teste");
    }

    @Test
    void testRecuperaTotens() {
        // Act
        List<Totem> lista = totemService.recuperaTotens();

        // Assert
        assertEquals(lista.size(), 3);
        assertEquals(lista.get(0), totens.get(0));
    }

    @Test
    void testRecuperaTrancasDoTotem() {
        // Arrange
        List<Tranca> trancasMock = new ArrayList<>();

        trancasMock.add(trancaMock1);
        trancasMock.add(trancaMock2);
        trancasMock.add(trancaMock3);

        when(totemService.recuperaTotemPorId(1)).thenReturn(totemMock);
        when(totemService.recuperaTrancasDoTotem(1)).thenReturn(trancasMock);
        when(trancaMock1.getId()).thenReturn(1);

        // Act
        List<Tranca> trancas = totemService.recuperaTrancasDoTotem(1);

        // Assert
        assertEquals(trancas.size(), 3);
        assertEquals(trancas.get(0).getId(), 1);
    }

    @Test
    void testRecuperaBicicletasDoTotem() {
        // Arrange
        List<Tranca> trancasMock = new ArrayList<>();
        List<Bicicleta> bicicletasMock = new ArrayList<>();

        trancasMock.add(trancaMock1);
        trancasMock.add(trancaMock2);
        trancasMock.add(trancaMock3);

        bicicletasMock.add(bicicletaMock1);
        bicicletasMock.add(bicicletaMock2);
        bicicletasMock.add(bicicletaMock3);

        when(bicicletaMock1.getId()).thenReturn(1);
        when(trancaMock1.getBicicleta()).thenReturn(1);

        when(bicicletaMock2.getId()).thenReturn(2);
        when(trancaMock2.getBicicleta()).thenReturn(2);

        when(trancaMock3.getBicicleta()).thenReturn(0);


        when(totemService.recuperaTotemPorId(1)).thenReturn(totemMock);
        when(totemService.recuperaTrancasDoTotem(1)).thenReturn(trancasMock);

        when(bicicletaService.recuperaBicicletaPorId(1)).thenReturn(bicicletaMock1);
        when(bicicletaService.recuperaBicicletaPorId(2)).thenReturn(bicicletaMock2);

        // Act
        List<Bicicleta> bicicletas = totemService.recuperaBicicletasDoTotem(1);

        // Assert
        assertEquals(bicicletas.size(), 2);
        assertEquals(bicicletas.get(0).getId(), 1);
        assertEquals(bicicletas.get(1).getId(), 2);
    }
}
