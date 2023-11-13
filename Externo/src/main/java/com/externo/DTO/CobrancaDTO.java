package com.externo.DTO;

import com.externo.model.Cobranca;

public record CobrancaDTO(Cobranca.StatusCobranca status, String horaSolicitacao, String horaFinalizacao, float valor, int ciclista) {}
