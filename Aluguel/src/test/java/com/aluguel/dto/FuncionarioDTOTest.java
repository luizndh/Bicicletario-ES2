package com.aluguel.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FuncionarioDTOTest {
    
    @Test
    void testFuncionarioDTO() {
        String matricula = "123456";
        String senha = "senhaTeste123";
        String confirmacaoSenha = "senhaTeste123";
        String email = "raphito@arroz.com";
        String nome = "raphito";
        int idade = 30;
        String funcao = "Gerente";
        String cpf = "777.777.777-77";
        
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(matricula, senha, confirmacaoSenha, email, nome, idade, funcao, cpf);
        
        assertEquals(matricula, funcionarioDTO.matricula());
        assertEquals(senha, funcionarioDTO.senha());
        assertEquals(confirmacaoSenha, funcionarioDTO.confirmacaoSenha());
        assertEquals(email, funcionarioDTO.email());
        assertEquals(nome, funcionarioDTO.nome());
        assertEquals(idade, funcionarioDTO.idade());
        assertEquals(funcao, funcionarioDTO.funcao());
        assertEquals(cpf, funcionarioDTO.cpf());
    }
}