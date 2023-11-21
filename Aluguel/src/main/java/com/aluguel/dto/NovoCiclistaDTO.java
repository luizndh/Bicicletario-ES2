package com.aluguel.dto;

import com.aluguel.model.Passaporte;

public record NovoCiclistaDTO(String nome, String nascimento, String cpf, Passaporte passaporte, String nacionalidade, String email, String urlFotoDocumento) {
    public NovoCiclistaDTO() {
        this("", "", "", new Passaporte("", "", ""), "","","");
    }
}