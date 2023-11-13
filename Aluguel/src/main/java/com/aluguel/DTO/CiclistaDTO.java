package com.aluguel.DTO;

import com.aluguel.model.Passaporte;
import com.aluguel.model.Ciclista.StatusCiclista;

public record CiclistaDTO (StatusCiclista status, String nome, String nascimento, String cpf, Passaporte passaporte, String nacionalidade, String email, String urlFotoDocumento) {}
