package com.externo.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> tratarErro422(IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse(422, "Argumento invalido");
        return ResponseEntity.status(error.getCodigo()).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> tratarErro404(NoSuchElementException e) {
        ErrorResponse error = new ErrorResponse(404, "Entidade nao existe");
        return ResponseEntity.status(error.getCodigo()).body(error);
    }
}
