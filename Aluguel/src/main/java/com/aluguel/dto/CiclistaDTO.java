package com.aluguel.dto;

import com.aluguel.model.Passaporte;

public record CiclistaDTO (int idCiclista, String status, String nome, String nascimento, String cpf, Passaporte passaporte, String nacionalidade, String email, String urlFotoDocumento, String senha) {}