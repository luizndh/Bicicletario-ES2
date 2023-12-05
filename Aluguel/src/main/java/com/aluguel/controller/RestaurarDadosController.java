package com.aluguel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguel.service.AluguelService;
import com.aluguel.service.CiclistaService;
import com.aluguel.service.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/restaurarDados")
@Api(value="API para requisicoes restaurar dados de teste")
public class RestaurarDadosController {

    @Autowired
    private AluguelService aluguelService;

    @Autowired
    private CiclistaService ciclistaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping(value = "")
    @ApiOperation(value="Restaura dados de teste no sistema")
    public ResponseEntity<?> registraAluguel() {
        this.ciclistaService.restauraDados();
        this.funcionarioService.restauraDados();
        this.aluguelService.restauraDados();
        return ResponseEntity.ok().body("Dados restaurados com sucesso!");
    }
}
