package com.aluguel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.aluguel.dto.FuncionarioDTO;
import com.aluguel.dto.NovoFuncionarioDTO;
import com.aluguel.model.Funcionario;
import com.aluguel.service.FuncionarioService;

@SpringBootTest
@AutoConfigureMockMvc
public class FuncionarioControllerTest {

    @MockBean
    private FuncionarioService funcionarioService;

    @Autowired
    private MockMvc mvc;

    final String JSON_ERRO_422 = "{\"codigo\":422,\"mensagem\":\"Argumento invalido\"}";
    final String JSON_ERRO_404 = "{\"codigo\":404,\"mensagem\":\"Entidade nao existe\"}";

    @Test
    public void testRecuperaFuncionarioPorMatricula() throws Exception {
        // Arrange
        String matriculaFuncionario = "1";
        when(funcionarioService.recuperaFuncionarioPorMatricula(matriculaFuncionario)).thenReturn(new Funcionario(new NovoFuncionarioDTO("123", "123", "lulusantos@hotmail.com", "Lulu Santos", "68", "Gerente", "123.456.789-00")));
        
        String json = "{\"matricula\":\"1\",\"senha\":\"123\",\"confirmacaoSenha\":\"123\",\"email\":\"lulusantos@hotmail.com\",\"nome\":\"Lulu Santos\",\"idade\":68,\"funcao\":\"Gerente\",\"cpf\":\"123.456.789-00\"}";

        // Act
        var response = this.mvc.perform(get("/funcionario/{matriculaFuncionario}", 1)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    public void testRecuperaFuncionarios() throws Exception {
        // Arrange
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario(new NovoFuncionarioDTO("123", "123", "lulusantos@hotmail.com", "Lulu Santos", "68", "Gerente", "123.456.789-00")));
        when(funcionarioService.recuperaFuncionarios()).thenReturn(funcionarios);

        String json = "[{\"matricula\":\"1\",\"senha\":\"123\",\"confirmacaoSenha\":\"123\",\"email\":\"lulusantos@hotmail.com\",\"nome\":\"Lulu Santos\",\"idade\":68,\"funcao\":\"Gerente\",\"cpf\":\"123.456.789-00\"}]";

        // Act
        var response = this.mvc.perform(get("/funcionario")).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    public void testCadastraFuncionario() throws Exception {
        // Arrange
        NovoFuncionarioDTO novoFuncionarioDTO = new NovoFuncionarioDTO("123", "123", "lulusantos@hotmail.com", "Lulu Santos", "68", "Gerente", "123.456.789-00");
        Funcionario funcionario = new Funcionario(novoFuncionarioDTO);
        when(funcionarioService.cadastraFuncionario(novoFuncionarioDTO)).thenReturn(funcionario);

        String json = "{\"matricula\":\"1\",\"senha\":\"123\",\"confirmacaoSenha\":\"123\",\"email\":\"lulusantos@hotmail.com\",\"nome\":\"Lulu Santos\",\"idade\":68,\"funcao\":\"Gerente\",\"cpf\":\"123.456.789-00\"}";

        // Act
        var response = this.mvc.perform(post("/funcionario")
                .contentType("application/json")
                .content(json))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    public void testAlteraFuncionario() throws Exception {
        // Arrange
        String matriculaFuncionario = "1";
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO("1", "123", "123", "lulusantos@hotmail.com", "Lulu Santos", "68", "Gerente", "123.456.789-00");
        Funcionario funcionario = new Funcionario(new NovoFuncionarioDTO("123", "123", "lulusantos@hotmail.com", "Lulu Santos", "68", "Gerente", "123.456.789-00"));
        when(funcionarioService.alteraFuncionario(matriculaFuncionario, funcionarioDTO)).thenReturn(funcionario);

        String json = "{\"matricula\":\"1\",\"senha\":\"123\",\"confirmacaoSenha\":\"123\",\"email\":\"lulusantos@hotmail.com\",\"nome\":\"Lulu Santos\",\"idade\":68,\"funcao\":\"Gerente\",\"cpf\":\"123.456.789-00\"}";

        // Act
        var response = this.mvc.perform(put("/funcionario/{matriculaFuncionario}", matriculaFuncionario)
                .contentType("application/json")
                .content(json))
                .andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals(json, response.getContentAsString());
    }

    @Test
    public void testExcluiFuncionario() throws Exception {
        // Arrange
        String matriculaFuncionario = "1";
        when(funcionarioService.excluiFuncionario(matriculaFuncionario)).thenReturn(true);

        // Act
        var response = this.mvc.perform(delete("/funcionario/{matriculaFuncionario}", matriculaFuncionario)).andReturn().getResponse();

        // Assert
        assertEquals(200, response.getStatus());
    }
}
