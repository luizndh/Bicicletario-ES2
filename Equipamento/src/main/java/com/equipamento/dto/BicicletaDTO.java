package com.equipamento.dto;

public record BicicletaDTO (String marca, String modelo, String ano, int numero, String status) {
    public BicicletaDTO() {
        this("", "", "", 0, "");
    }
}
