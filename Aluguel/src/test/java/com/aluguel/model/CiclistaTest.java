package com.aluguel.model;

import com.aluguel.DTO.CiclistaDTO;
import com.aluguel.model.Ciclista.StatusCiclista;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CiclistaTest {

    private Ciclista ciclista;

    @BeforeEach
    public void setUp() {
        CiclistaDTO dadosCadastroCiclista = new CiclistaDTO(StatusCiclista.AGUARDANDO_CONFIRMACAO,"Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("2345678","12/2025", "BR"), "Brasileiro", "luisfumado@teste.com", "https://teste.com/foto.jpg");
        ciclista = new Ciclista(dadosCadastroCiclista);
    }

    @Test
    public void testAtualizaCiclista() {
        CiclistaDTO dadosAlteracaoCiclista = new CiclistaDTO(StatusCiclista.ATIVO,"Luisa Fumada", "31/12/2001", "009.876.543-21", new Passaporte("8765432","12/2025", "BR"), "Brasileira", "luisafumada@teste.com", "http://url-da-foto.com");


        ciclista.atualizaCiclista(dadosAlteracaoCiclista);

        assertEquals("Luisa Fumada", ciclista.getNome());
        assertEquals("31/12/2001", ciclista.getNascimento());
        assertEquals("009.876.543-21", ciclista.getCpf());
        assertEquals("Brasileira", ciclista.getNacionalidade());
        assertEquals("luisafumada@teste.com", ciclista.getEmail());
        assertEquals("http://url-da-foto.com", ciclista.getUrlFotoDocumento());
    }

    @Test
    public void testGetId() {
        assertEquals(Ciclista.ciclistas.size()+1, ciclista.getId());
    }

    @Test
    public void testGetStatus() {
        assertEquals(Ciclista.StatusCiclista.AGUARDANDO_CONFIRMACAO, ciclista.getStatus());
    }

    @Test
    public void testGetNome() {
        assertEquals("Luis Fumado", ciclista.getNome());
    }

    @Test
    public void testGetNascimento() {
        assertEquals("01/01/2000", ciclista.getNascimento());
    }

    @Test
    public void testGetCpf() {
        assertEquals("123.456.789-00", ciclista.getCpf());
    }

    @Test
    public void testGetPassaporte() {
        assertEquals(new Passaporte("2345678","12/2025", "BR").getNumero(), ciclista.getPassaporte().getNumero());
    }

    @Test
    public void testGetNacionalidade() {
        assertEquals("Brasileiro", ciclista.getNacionalidade());
    }

    @Test
    public void testGetEmail() {
        assertEquals("luisfumado@teste.com", ciclista.getEmail());
    }

    @Test
    public void testGetUrlFotoDocumento() {
        assertEquals("https://teste.com/foto.jpg", ciclista.getUrlFotoDocumento());
    }
}
