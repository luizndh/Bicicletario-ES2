package com.externo.controller;

import com.externo.DTO.CobrancaDTO;
import com.externo.model.Cobranca;
import com.externo.servico.CobrancaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Api(value="API para requisicoes de cobranca")
public class CobrancaController {

    @Autowired
    private CobrancaService service;

    @PostMapping("/cobranca")
    @ApiOperation(value="Realiza a cobranca")
    public ResponseEntity<Boolean> realizaCobranca(@RequestBody CobrancaDTO dadosCobranca) {
        return ResponseEntity.ok().body(this.service.realizaCobranca(dadosCobranca));
    }

    @GetMapping("/cobranca/{idCobranca}")
    @ApiOperation(value="Obtem os dados da cobranca pelo id")
    public ResponseEntity<Cobranca> obterCobranca(@PathVariable int idCobranca) {
        return ResponseEntity.ok().body(this.service.obterCobranca(idCobranca));
    }

    @PostMapping("/processaCobrancasEmFila")
    @ApiOperation(value="Processa todas as cobrancas atradadas colocadas em fila anteriormente")
    public ResponseEntity<Boolean> processaCobrancasEmFila() {
        return ResponseEntity.ok().body(this.service.processaCobrancasEmFila());
    }

    @PostMapping("/filaCobranca")
    @ApiOperation(value="Inclui cobranca na fila de cobranca")
    public ResponseEntity<Boolean> incluiFilaCobranca(@RequestBody CobrancaDTO dadosCobranca) {
        return ResponseEntity.ok().body(this.service.incluiFilaCobranca(dadosCobranca));
    }
}
