package com.aluguel.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.service.CartaoDeCreditoService;
//TODO nao sei se vai ficar esse teste
@ExtendWith(MockitoExtension.class)
public class CartaoDeCreditoControllerTest {

    @Mock
    private CartaoDeCreditoService service;

    @Mock
    private CartaoDeCredito cartaoDeCredito;

    @InjectMocks
    private CartaoDeCreditoController controller;

    private MockMvc mockMvc;

    @Test
    public void testRecuperaCartaoDeCreditoPorId() throws Exception {
        int idCiclista = 1;
        when(service.recuperaCartaoDeCreditoPorId(idCiclista)).thenReturn(cartaoDeCredito);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/cartaoDeCredito/" + idCiclista)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
