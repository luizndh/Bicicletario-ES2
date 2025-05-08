package com.externo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.externo.dto.CobrancaDTO;
import com.externo.model.Cobranca;
import com.externo.servico.CobrancaService;
import com.stripe.exception.StripeException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
@Api(value="API para requisicoes de cobranca")
public class CobrancaController {

    @Autowired
    private CobrancaService service;

    @PostMapping(value = "/cobranca", consumes = "application/json")
    @ApiOperation(value="Realiza a cobranca")
    public ResponseEntity<Cobranca> realizaCobranca(@RequestBody CobrancaDTO dadosCobranca) throws StripeException {
        //TODO em andamento
        Cobranca dados = new Cobranca(dadosCobranca);

        Cobranca resultado = this.service.realizaCobranca(dados);

        return ResponseEntity.ok().body(resultado);
    }

    @GetMapping("/cobranca/{idCobranca}")
    @ApiOperation(value="Obtem os dados da cobranca pelo id")
    public ResponseEntity<Cobranca> obterCobranca(@PathVariable int idCobranca) {
        return ResponseEntity.ok().body(this.service.obterCobranca(idCobranca));
    }

    @PostMapping("/processaCobrancasEmFila")
    @ApiOperation(value="Processa todas as cobrancas atradadas colocadas em fila anteriormente")
    public ResponseEntity<List<Cobranca>> processaCobrancasEmFila() throws StripeException {
        return ResponseEntity.ok().body(this.service.processaCobrancasEmFila());
    }

    @PostMapping("/filaCobranca")
    @ApiOperation(value="Inclui cobranca na fila de cobranca")
    public ResponseEntity<Cobranca> incluiFilaCobranca(@RequestBody CobrancaDTO dadosCobranca) {
        return ResponseEntity.ok().body(this.service.incluiFilaCobranca(dadosCobranca));
    }
}
