package com.aluguel.DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FuncionarioDTOTest {
    
    @Test
    public void testFuncionarioDTO() {
        String senha = "batoEmIdosas123";
        String confirmacaoSenha = "batoEmIdosas123";
        String email = "raphito@arroz.com";
        String nome = "raphito";
        int idade = 30;
        String funcao = "Gerente";
        String cpf = "777.777.777-77";
        
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(senha, confirmacaoSenha, email, nome, idade, funcao, cpf);
        
        Assertions.assertEquals(senha, funcionarioDTO.senha());
        Assertions.assertEquals(confirmacaoSenha, funcionarioDTO.confirmacaoSenha());
        Assertions.assertEquals(email, funcionarioDTO.email());
        Assertions.assertEquals(nome, funcionarioDTO.nome());
        Assertions.assertEquals(idade, funcionarioDTO.idade());
        Assertions.assertEquals(funcao, funcionarioDTO.funcao());
        Assertions.assertEquals(cpf, funcionarioDTO.cpf());
    }
}