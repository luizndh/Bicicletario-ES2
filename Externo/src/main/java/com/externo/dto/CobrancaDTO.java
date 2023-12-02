package com.externo.dto;

import com.externo.model.Cobranca;
//IMPORTANTE: valor em centavos
public record CobrancaDTO(String status, String horaSolicitacao, String horaFinalizacao, Long valor, Integer ciclista) {}
