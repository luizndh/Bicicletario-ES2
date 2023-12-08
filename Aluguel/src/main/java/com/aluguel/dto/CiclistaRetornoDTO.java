package com.aluguel.dto;

import com.aluguel.model.Passaporte;

public record CiclistaRetornoDTO (int idCiclista, String nome, String nascimento, String cpf, Passaporte passaporte, String nacionalidade, String email, String urlFotoDocumento) {}
