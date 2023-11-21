package com.aluguel.dto;

public record FuncionarioDTO(String matricula, String senha, String confirmacaoSenha, String email, String nome, int idade, String funcao, String cpf) {}