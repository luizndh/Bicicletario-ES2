package com.aluguel.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class NovoFuncionarioDTOTest {

    @Test
    public void testNovoFuncionarioDTO() {
        // Arrange
        String senha = "password";
        String confirmacaoSenha = "password";
        String email = "test@example.com";
        String nome = "John Doe";
        int idade = 30;
        String funcao = "Manager";
        String cpf = "123456789";

        // Act
        NovoFuncionarioDTO novoFuncionarioDTO = new NovoFuncionarioDTO(senha, confirmacaoSenha, email, nome, idade, funcao, cpf);

        // Assert
        assertEquals(senha, novoFuncionarioDTO.senha());
        assertEquals(confirmacaoSenha, novoFuncionarioDTO.confirmacaoSenha());
        assertEquals(email, novoFuncionarioDTO.email());
        assertEquals(nome, novoFuncionarioDTO.nome());
        assertEquals(idade, novoFuncionarioDTO.idade());
        assertEquals(funcao, novoFuncionarioDTO.funcao());
        assertEquals(cpf, novoFuncionarioDTO.cpf());
    }
}