package com.aluguel.model;

import com.aluguel.dto.CiclistaDTO;
import com.aluguel.dto.NovoCiclistaDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CiclistaTest {

    private Ciclista ciclista;

    @BeforeEach
    void setUp() {
        NovoCiclistaDTO dadosCadastroCiclista = new NovoCiclistaDTO("Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("", "", ""), "Brasileiro", "luisfumado@teste.com", "12345678");
        ciclista = new Ciclista(dadosCadastroCiclista);
    }

    @Test
    void testAtualizaCiclista() {
        CiclistaDTO dadosAlteracaoCiclista = new CiclistaDTO(1, "ATIVO","Luisa Fumada", "31/12/2001", "009.876.543-21", new Passaporte("", "", ""), "Brasileira", "luisafumada@teste.com", "http://url-da-foto.com", "12345678");


        ciclista.atualizaCiclista(dadosAlteracaoCiclista);

        assertEquals("Luisa Fumada", ciclista.getNome());
        assertEquals("31/12/2001", ciclista.getNascimento());
        assertEquals("009.876.543-21", ciclista.getCpf());
        assertEquals("Brasileira", ciclista.getNacionalidade());
        assertEquals("luisafumada@teste.com", ciclista.getEmail());
        assertEquals("http://url-da-foto.com", ciclista.getUrlFotoDocumento());
    }

    @Test
    void testGetId() {
        assertEquals(Ciclista.ciclistas.size()+1, ciclista.getId());
    }

    @Test
    void testGetStatus() {
        assertEquals(Ciclista.StatusCiclista.AGUARDANDO_CONFIRMACAO, ciclista.getStatus());
    }

    @Test
    void testGetNome() {
        assertEquals("Luis Fumado", ciclista.getNome());
    }

    @Test
    void testGetNascimento() {
        assertEquals("01/01/2000", ciclista.getNascimento());
    }

    @Test
    void testGetCpf() {
        assertEquals("123.456.789-00", ciclista.getCpf());
    }

    @Test
    void testGetNacionalidade() {
        assertEquals("Brasileiro", ciclista.getNacionalidade());
    }

    @Test
    void testGetEmail() {
        assertEquals("luisfumado@teste.com", ciclista.getEmail());
    }

    @Test
    void testGetUrlFotoDocumento() {
        assertEquals("https://teste.com/foto.jpg", ciclista.getUrlFotoDocumento());
    }
}
