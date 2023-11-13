package com.aluguel.model;

import com.aluguel.DTO.FuncionarioDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    public void setUp() {
        FuncionarioDTO dadosCadastroFuncionario = new FuncionarioDTO("batoEmIdosas123", "batoEmIdosas123", "raphito@arroz.com", "raphito", 30, "Gerente", "777.777.777-77");
        funcionario = new Funcionario(dadosCadastroFuncionario);
    }

    @Test
    public void testGetMatricula() {
        assertEquals(String.valueOf(Funcionario.funcionarios.size()+1), funcionario.getMatricula());
    }

    @Test
    public void testAtualizaFuncionario() {
        FuncionarioDTO dadosAlteracaoFuncionario = new FuncionarioDTO("vouMeMatar123", "vouMeMatar123", "raphito@feijao.com", "otihpar", 3, "escravo", "000.000.000-00");

        funcionario.atualizaFuncionario(dadosAlteracaoFuncionario);

        assertEquals("vouMeMatar123", funcionario.getSenha());
        assertEquals("vouMeMatar123", funcionario.getConfirmacaoSenha());
        assertEquals("raphito@feijao.com", funcionario.getEmail());
        assertEquals("otihpar", funcionario.getNome());
        assertEquals(3, funcionario.getIdade());
        assertEquals("escravo", funcionario.getFuncao());
        assertEquals("000.000.000-00", funcionario.getCpf());
    }
}
