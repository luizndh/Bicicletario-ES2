package com.aluguel.dto;

public record CiclistaDTO (int idCiclista, String status, String nome, String nascimento, String cpf, String nacionalidade, String email, String urlFotoDocumento, String senha, CartaoDeCreditoDTO cartaoDeCredito) {}