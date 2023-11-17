package com.externo.dto;

import com.externo.model.Cobranca;

public record CobrancaDTO(String status, String horaSolicitacao, String horaFinalizacao, float valor, int ciclista) {}
