package com.aluguel.dto;

public record NovoFuncionarioDTO(String senha, String confirmacaoSenha, String email, String nome, int idade, String funcao, String cpf) {}