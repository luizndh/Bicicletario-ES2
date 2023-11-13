package com.aluguel.service;

import com.aluguel.DTO.CiclistaDTO;
import com.aluguel.model.Ciclista;
import com.aluguel.model.Ciclista.StatusCiclista;
import com.aluguel.model.Passaporte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CiclistaServiceTest {

    @Mock
    private CiclistaService ciclistaService;

    @Mock
    private List<Ciclista> ciclistas;

    @BeforeEach
    public void setUp() {
        // Arrange
        CiclistaDTO ciclistaDTO1 = new CiclistaDTO(StatusCiclista.ATIVO, "Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("1","12/2025", "BR"), "Brasileiro", "luisfumado@teste.com", "https://teste.com/foto.jpg");
        CiclistaDTO ciclistaDTO2 = new CiclistaDTO(StatusCiclista.AGUARDANDO_CONFIRMACAO, "Luisa Fumada", "31/12/2001", "009.876.543-21", new Passaporte("2","12/2025", "BR"), "Brasileira", "luisafumada@testa.com", "https://testa.com/foto.jpg");
        CiclistaDTO ciclistaDTO3 = new CiclistaDTO(StatusCiclista.INATIVO, "Lucas Sacul", "01/01/1885", "256.969.691-23", new Passaporte("3","12/2025", "BR"), "Brasileiro", "luckhaos@testo.com", "https://testo.com/foto.jpg");

        ciclistas = new ArrayList<Ciclista>();
        ciclistas.add(new Ciclista(ciclistaDTO1));
        ciclistas.add(new Ciclista(ciclistaDTO2));
        ciclistas.add(new Ciclista(ciclistaDTO3));
    }

    @Test
    public void testRecuperaCiclistaPorId() {
        when(ciclistaService.recuperaCiclistaPorId(3)).thenReturn(ciclistas.get(2));

        Ciclista ciclistaRecuperado = ciclistaService.recuperaCiclistaPorId(3);

        assertNotNull(ciclistaRecuperado);
        assertEquals("Lucas Sacul", ciclistaRecuperado.getNome());
    }

    @Test
    public void testRecuperaCiclistas() {
        // Arrange
        when(ciclistaService.recuperaCiclistas()).thenReturn(ciclistas);
        
        // Act
        List<Ciclista> ciclistasRecuperados = ciclistaService.recuperaCiclistas();

        // Assert
        assertNotNull(ciclistasRecuperados);
        assertEquals(3, ciclistasRecuperados.size());
    }

    @Test
    public void testCadastraCiclista() {
        // Arrange
        CiclistaDTO dadosCadastroCiclista = new CiclistaDTO(StatusCiclista.ATIVO, "Brenin da 17", "17/17/1717", "171.717.171.71", new Passaporte("17","17/1717", "BR"), "Brasileiro até D+", "brenin17@testando.com", "https://teste.com/17.jpg");
        when(ciclistaService.cadastraCiclista(dadosCadastroCiclista)).then(invocation -> {
            Ciclista novoCiclista = new Ciclista(dadosCadastroCiclista);
            ciclistas.add(novoCiclista);
            return novoCiclista;
        });

        // Act
        Ciclista ciclista = ciclistaService.cadastraCiclista(dadosCadastroCiclista);

        // Assert
        assertNotNull(ciclista);
        assertEquals("Brenin da 17", ciclista.getNome());
        assertTrue(ciclistas.contains(ciclista));
        assertEquals(4, ciclistas.size());
    }

    @Test
    public void testAlteraCiclista() {
        // Arrange
        CiclistaDTO dadosAlteracaoCiclista = new CiclistaDTO(StatusCiclista.INATIVO, "Klein Tel Aviv", "11/09/2001", "234.432.234-00", new Passaporte("11092001","09/2001", "IL"), "Israelense", "kleine@estrela.com", "https://teste.com/pog.jpg");
        when(ciclistaService.alteraCiclista(1, dadosAlteracaoCiclista)).then(invocation -> {
            Ciclista ciclista = ciclistas.get(0);
            ciclista.atualizaCiclista(dadosAlteracaoCiclista);
            return ciclista;
        });

        // Act
        Ciclista ciclista = ciclistaService.alteraCiclista(1, dadosAlteracaoCiclista);

        // Assert
        assertNotNull(ciclista);
        assertEquals("Klein Tel Aviv", ciclista.getNome());
        assertEquals("11/09/2001", ciclista.getNascimento());
        assertEquals("234.432.234-00", ciclista.getCpf());
        assertEquals("11092001", ciclista.getPassaporte().getNumero());
        assertEquals("Israelense", ciclista.getNacionalidade());
        assertEquals("kleine@estrela.com", ciclista.getEmail());
        assertEquals("https://teste.com/pog.jpg", ciclista.getUrlFotoDocumento());
        assertEquals(3, ciclistas.size());
    }
}
