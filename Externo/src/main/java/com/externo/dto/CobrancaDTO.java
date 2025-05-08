package com.externo.dto;

//IMPORTANTE: valor em centavos
public record CobrancaDTO(String status, String horaSolicitacao, String horaFinalizacao, Long valor, Integer ciclista) {}
