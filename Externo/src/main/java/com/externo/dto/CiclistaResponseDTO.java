package com.externo.dto;

public record CiclistaResponseDTO (int idCiclista, String status, String nome, String nascimento, String cpf, PassaporteResponseDTO passaporte, String nacionalidade, String email, String urlFotoDocumento) {}