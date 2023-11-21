package com.aluguel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguel.dto.NovoAluguelDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.service.AluguelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/aluguel")
@Api(value="API para requisicoes relacionadas a alugueis")
public class AluguelController {

    @Autowired
    private AluguelService service;

    @PostMapping(value = "", consumes = "application/json")
    @ApiOperation(value="Registra um novo aluguel no sistema")
    public ResponseEntity<Aluguel> registraAluguel(@RequestBody NovoAluguelDTO dadosCadastroAluguel) {
        return ResponseEntity.ok().body(this.service.registraAluguel(dadosCadastroAluguel));
    }
}
