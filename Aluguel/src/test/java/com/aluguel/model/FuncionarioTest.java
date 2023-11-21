package com.aluguel.model;

import com.aluguel.dto.NovoFuncionarioDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FuncionarioTest {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        NovoFuncionarioDTO dadosCadastroFuncionario = new NovoFuncionarioDTO("senhaTeste123", "senhaTeste123", "raphito@arroz.com", "raphito", 30, "Gerente", "777.777.777-77");
        funcionario = new Funcionario(dadosCadastroFuncionario);
    }

    @Test
    void testGetMatricula() {
        assertEquals(String.valueOf(Funcionario.funcionarios.size()+1), funcionario.getMatricula());
    }

    @Test
    void testAtualizaFuncionario() {
        NovoFuncionarioDTO dadosAlteracaoFuncionario = new NovoFuncionarioDTO("testeSenha321", "testeSenha321", "raphito@feijao.com", "otihpar", 3, "garçom", "000.000.000-00");

        funcionario.atualizaFuncionario(dadosAlteracaoFuncionario);

        assertEquals("testeSenha321", funcionario.getSenha());
        assertEquals("testeSenha321", funcionario.getConfirmacaoSenha());
        assertEquals("raphito@feijao.com", funcionario.getEmail());
        assertEquals("otihpar", funcionario.getNome());
        assertEquals(3, funcionario.getIdade());
        assertEquals("garçom", funcionario.getFuncao());
        assertEquals("000.000.000-00", funcionario.getCpf());
    }
}
