package com.externo.dto;

public record CiclistaResponseDTO (int id, String status, String nome, String nascimento, String cpf, String nacionalidade, String email, String urlFotoDocumento, String senha, CartaoDeCreditoResponseDTO cartaoDeCredito, boolean ativo) {}