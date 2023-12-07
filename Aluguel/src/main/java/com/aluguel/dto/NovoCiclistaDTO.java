package com.aluguel.dto;

import com.aluguel.model.Passaporte;

public record NovoCiclistaDTO(String nome, String nascimento, String cpf, Passaporte passaporte, String nacionalidade, String email, String senha) {
    public NovoCiclistaDTO() {
        this("", "", "", null,"","", "");
    }
}