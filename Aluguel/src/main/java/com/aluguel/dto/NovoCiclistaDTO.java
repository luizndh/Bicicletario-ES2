package com.aluguel.dto;

public record NovoCiclistaDTO(String nome, String nascimento, String cpf, String nacionalidade, String email, String senha, CartaoDeCreditoDTO cartaoDeCredito) {
    public NovoCiclistaDTO() {
        this("", "", "", "","","", null);
    }
}