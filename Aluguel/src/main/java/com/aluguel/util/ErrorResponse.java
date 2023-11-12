package com.aluguel.util;

public class ErrorResponse {
    private int codigo;
    private String mensagem;

    public ErrorResponse(int codigo, String mensagem) {
        this.codigo = codigo;
        this.mensagem = mensagem;
    }

    public int getCodigo() {
        return this.codigo;
    }
    public String getMensagem() {
        return this.mensagem;
    }
}
