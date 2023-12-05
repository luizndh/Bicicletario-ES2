package com.aluguel.controller;

import com.aluguel.service.CiclistaService;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
class AluguelControllerTest {

    @MockBean
    private CiclistaService ciclistaService;

    // @Autowired
    // private MockMvc mvc;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    // @Test
    // void registraAluguel() throws Exception {
    //     // Arrange
    //     when(ciclistaService.recuperaCiclistaPorId(1)).thenReturn(new Ciclista(new NovoCiclistaDTO("Luis Fumado", "01/01/2000", "123.456.789-00", new Passaporte("1","12/2025", "BR"), "Brasileiro", "luizfumado@gmail.com", "https://teste.com/foto.jpg", "12345678")));
    //     //String horaInicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //     String jsonEntrada = """
    //             {
    //                 "ciclista": "1",
    //                 "trancaInicio": "1"
    //             }
    //             """;
    //     //String json = "{\"bicicleta\":1,\"horaInicio\":\""+horaInicio+"\",\"trancaFim\":-1,\"horaFim\":\"\",\"cobranca\":10,\"ciclista\":1,\"trancaInicio\":1}";

    //     // Act
    //     var response = this.mvc.perform(post("/aluguel").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    //     // Assert
    //     assertEquals(200, response.getStatus());
    //     //assertEquals(json, response.getContentAsString());
    // }
}