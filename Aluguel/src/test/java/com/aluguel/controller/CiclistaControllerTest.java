package com.aluguel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import com.aluguel.Integracoes;
import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.NovoCiclistaDTO;
import com.aluguel.model.Ciclista;
import com.aluguel.model.Passaporte;
import com.aluguel.service.CiclistaService;

@SpringBootTest
@AutoConfigureMockMvc
public class CiclistaControllerTest {

    @Mock
    private Integracoes integracoes;

    @MockBean
    private CiclistaService ciclistaService;

    @Autowired
    private MockMvc mvc;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @Test
    public void testRecuperaCiclistaPorId() throws Exception{
        // Arrange
        int idCiclista = 1;
        when(ciclistaService.recuperaCiclistaPorId(idCiclista)).thenReturn(new Ciclista(new NovoCiclistaDTO("Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("", "", ""), "Brasileiro", "luisfumado@gamil.com", "12345678")));

        String json = "{\"id\":1,\"status\":\"AGUARDANDO_CONFIRMACAO\",\"nome\":\"Luis Fumado\",\"nascimento\":\"01/01/2000\",\"cpf\":\"123.456.789-00\",\"passaporte\":{\"numero\":\"1\",\"validade\":\"12/2025\",\"pais\":\"BR\"},\"nacionalidade\":\"Brasileiro\",\"email\":\"luisfumado@gamil.com\",\"urlFotoDocumento\":\"https://teste.com/foto.jpg\",\"senha\":\"12345678\",\"ativo\":false}";

        // Act
        var response = this.mvc.perform(get("/ciclista/{idCiclista}", 1)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }


    @Test
    public void testVerificaEmail() throws Exception {
        // Arrange
        String email = "test@example.com";
        when(ciclistaService.verificaEmail(email)).thenReturn(true);

        // Act
        var response = this.mvc.perform(get("/ciclista/existeEmail/{email}", email)).andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("true", response.getContentAsString());
    }

    // @Test
    // public void testCadastraCiclista() throws Exception {
    //     // Arrange
    //     NovoCiclistaDTO dadosCadastroCiclista = new NovoCiclistaDTO("Luis Fumado", "01/01/2000", "123.456.789-00", null, "Brasileiro", "luisfumado@gmail.com", "https://teste.com/foto.jpg", "12345678");
    //     CartaoDeCreditoDTO dadosCadastroCartao = new CartaoDeCreditoDTO("Luis Fumado", "1234567890123456", "12/2025", "123");
    //     Ciclista ciclista = new Ciclista(dadosCadastroCiclista);
    //     when(ciclistaService.cadastraCiclista(dadosCadastroCiclista, dadosCadastroCartao)).thenReturn(ciclista);

    //     String jsonEntrada = """
    //             {
    //                 "dadosCadastroCiclista":
    //                 {
    //                     "nome": "Luis Fumado",
    //                     "nascimento": "01/01/2000",
    //                     "cpf": "123.456.789-00",
    //                     "passaporte": null,
    //                     "nacionalidade": "Brasileiro",
    //                     "email": "luisfumado@gmail.com",
    //                     "urlFotoDocumento": "https://teste.com/foto.jpg"
    //                     "senha": "12345678"
    //                 },
    //                 "cartaoDeCredito":
    //                 {
    //                     "nome": "Luis Fumado",
    //                     "numero": "1234567890123456",
    //                     "validade": "12/2025",
    //                     "codigo": "123"
    //                 }
    //             }
    //             """;

    //     String json = "{{\"id\":1,\"status\":\"AGUARDANDO_CONFIRMACAO\",\"nome\":\"Luis Fumado\",\"nascimento\":\"01/01/2000\",\"cpf\":\"123.456.789-00\",\"passaporte\":null,\"nacionalidade\":\"Brasileiro\",\"email\":\"luisfumado@gmail.com\",\"urlFotoDocumento\":\"https://teste.com/foto.jpg\",\"senha\":\"12345678\",\"ativo\":false}{\"nome\":\"Luis Fumado\",\"numero\":\"1234567890123456\",\"validade\":\"12/2025\",\"codigo\":\"123\"}}";

    //     // Act
    //     var response = this.mvc.perform(post("/ciclista")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(jsonEntrada))
    //             .andReturn().getResponse();

    //     // Assert
    //     assertEquals(200, response.getStatus());
    //     assertEquals(json, response.getContentAsString());
    // }

    @Test
    public void testVerificaSeCiclistaPodeAlugar() throws Exception {
        // Arrange
        int idCiclista = 1;
        when(ciclistaService.verificaSeCiclistaPodeAlugar(idCiclista)).thenReturn(true);

        // Act
        var response = this.mvc.perform(get("/ciclista/{idCiclista}/permiteAluguel", idCiclista)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("true", response.getContentAsString());
    }

    @Test
    public void testAtivaCiclista() throws Exception {
        // Arrange
        int idCiclista = 1;
        Ciclista ciclista = new Ciclista(new NovoCiclistaDTO("Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("", "", ""), "Brasileiro", "luisfumado@gmail.com", "12345678"));
        
        when(ciclistaService.ativaCiclista(idCiclista)).then(invocation -> {
            ciclista.ativaCiclista();
            return ciclista;
        });

        String json = "{\"id\":1,\"status\":\"ATIVO\",\"nome\":\"Luis Fumado\",\"nascimento\":\"01/01/2000\",\"cpf\":\"123.456.789-00\",\"passaporte\":{\"numero\":\"1\",\"validade\":\"12/2025\",\"pais\":\"BR\"},\"nacionalidade\":\"Brasileiro\",\"email\":\"luisfumado@gmail.com\",\"urlFotoDocumento\":\"https://teste.com/foto.jpg\",\"senha\":\"12345678\",\"ativo\":true}";

        // Act
        var response = this.mvc.perform(post("/ciclista/{idCiclista}/ativar", idCiclista)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    // @Test
    // public void testAlteraCiclista() throws Exception {
    //     // Arrange
    //     int idCiclista = 1;
    //     CiclistaDTO ciclistaDTO = new CiclistaDTO(1, "ATIVO", "Luis Fumado", "01/01/2000", "123.456.789-00", null, "Brasileiro", "luisfumado@gmail.com", "https://teste.com/foto.jpg", "12345678");
    //     Ciclista ciclista = new Ciclista(new NovoCiclistaDTO("Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("1","12/2025", "BR"), "Brasileiro", "luisfumado@gmail.com", "https://teste.com/foto.jpg", "12345678"));
    //     when(ciclistaService.alteraCiclista(idCiclista, ciclistaDTO)).thenReturn(ciclista);

    //     String jsonEntrada = """
    //             {
    //                 "idCiclista": 1,
    //                 "status": "ATIVO",
    //                 "nome": "Luis Fumado",
    //                 "nascimento": "01/01/2000",
    //                 "cpf": "123.456.789-00",
    //                 "passaporte": null,
    //                 "nacionalidade": "Brasileiro",
    //                 "email": "luisfumado@gmail.com",
    //                 "urlFotoDocumento": "https://teste.com/foto.jpg"
    //                 "senha": "12345678"
    //             }
    //             """;
    //     String json = "{\"id\":1,\"status\":\"AGUARDANDO_CONFIRMACAO\",\"nome\":\"Luis Fumado\",\"nascimento\":\"01/01/2000\",\"cpf\":\"123.456.789-00\",\"passaporte\":{\"numero\":\"1\",\"validade\":\"12/2025\",\"pais\":\"BR\"},\"nacionalidade\":\"Brasileiro\",\"email\":\"luisfumado@gmail.com\",\"urlFotoDocumento\":\"https://teste.com/foto.jpg\",\"senha\":\"12345678\",\"ativo\":false}";

    //     // Act
    //     var response = this.mvc.perform(put("/ciclista/{idCiclista}", idCiclista)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(jsonEntrada))
    //             .andReturn().getResponse();

    //     // Assert
    //     assertEquals(200, response.getStatus());
    //     assertEquals(json, response.getContentAsString());
    // }

    @Test
    public void testRecuperaBicicletaAlugada() throws Exception {
        // Arrange
        int idCiclista = 1;
        when(ciclistaService.recuperaBicicletaAlugada(idCiclista)).thenReturn(new BicicletaDTO( "1", "Caloi", "Bicicleta de estrada", "2012", "12", "Em Uso"));

        String json = "{\"id\":\"1\",\"marca\":\"Caloi\",\"modelo\":\"Bicicleta de estrada\",\"ano\":\"2012\",\"numero\":\"12\",\"status\":\"Em Uso\"}";

        // Act
        var response = this.mvc.perform(get("/ciclista/ciclista/{idCiclista}/bicicletaAlugada", idCiclista)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }
}