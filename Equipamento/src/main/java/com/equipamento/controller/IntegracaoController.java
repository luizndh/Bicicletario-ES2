package com.equipamento.controller;

import com.equipamento.servico.IntegracaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurarDados")
@Api(value="API para restaurar os dados do sistema")
public class IntegracaoController {

    @Autowired
    IntegracaoService service;

    @GetMapping("")
    @ApiOperation(value="Restaura os dados do sistema")
    public void restaurarDados() {
        service.restaurarDados();
    }
}
