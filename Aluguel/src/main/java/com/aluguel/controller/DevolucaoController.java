package com.aluguel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguel.dto.NovaDevolucaoDTO;
import com.aluguel.model.Devolucao;
import com.aluguel.service.DevolucaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/devolucao")
@Api(value="API para requisicoes relacionadas a devoluções")
public class DevolucaoController {

    @Autowired
    private DevolucaoService service;

    @PostMapping(value = "", consumes = "application/json")
    @ApiOperation(value="Registra uma nova devolução no sistema")
    public ResponseEntity<Devolucao> registraDevolucao(@RequestBody NovaDevolucaoDTO dadosCadastroDevolucao) {
        return ResponseEntity.ok().body(this.service.registraDevolucao(dadosCadastroDevolucao));
    }
}
