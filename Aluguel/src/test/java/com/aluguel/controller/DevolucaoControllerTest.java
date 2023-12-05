package com.aluguel.controller;

import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.aluguel.Integracoes;
import com.aluguel.service.AluguelService;

@SpringBootTest
@AutoConfigureMockMvc
public class DevolucaoControllerTest {

    @MockBean
    private AluguelService aluguelService;

    @Mock
    private Integracoes integracoes;

    //@Autowired
    //private MockMvc mvc;

    // @Test
    // public void testRegistraDevolucao() throws Exception {
    //     // Arrange
    //     String idBicicleta = "1";
    //     when(integracoes.recuperaBicicletaPorId(Integer.parseInt(idBicicleta))).thenReturn(new BicicletaDTO("1", "marca", "modelo", "ano", "numero", "status"));
    //     when(integracoes.realizaCobranca(any(NovaCobrancaDTO.class))).thenReturn(new CobrancaDTO("1", "10:00", "11:00", "30", "1"));
    //     when(integracoes.trancaTranca(anyInt(),anyInt())).thenReturn(true);
    //     when(integracoes.enviaEmail(anyString(), anyString(), anyString())).thenReturn(true);
    //     when(aluguelService.recuperaAluguelPorIdBicicleta(Integer.parseInt(idBicicleta))).then(invocation -> {
    //         Aluguel aluguel = new Aluguel(new NovoAluguelDTO("1", "2"));
    //         aluguel.atualizaAluguel(new AluguelDTO("1", "10:00", "2", "11:00", "10", "3", "4"));
    //         return aluguel;
    //     });
    //     //String horaFim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //     String jsonEntrada = """
    //             {
    //                 "idBicicleta": "1",
    //                 "trancaFim": "1"
    //             }
    //             """;
    //     //String json = "{\"bicicleta\":1,\"horaInicio\":\"2021-10-10 10:10:10\",\"trancaFim\":1,\"horaFim\":\""+horaFim+"\",\"cobranca\":10,\"ciclista\":1}";

    //     // Act
    //     var response = this.mvc.perform(post("/devolucao").content(jsonEntrada).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

    //     // Assert
    //     assertEquals(200, response.getStatus());
    //     //assertEquals(json, response.getContentAsString());
    // }
}