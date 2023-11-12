package com.aluguel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguel.DTO.CartaoDeCreditoDTO;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.service.CartaoDeCreditoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/cartaoDeCredito")
@Api(value="API para requisicoes relacionadas ao cartao de credito")
public class CartaoDeCreditoController {

    @Autowired
    private CartaoDeCreditoService service;

    @GetMapping("/{idCiclista}")
    @ApiOperation(value="Retorna um cartao de crédito com o id passado")
    public ResponseEntity<CartaoDeCredito> recuperaCartaoDeCreditoPorId(@PathVariable int idCiclista) {
        CartaoDeCredito cartaoDeCredito = this.service.recuperaCartaoDeCreditoPorId(idCiclista);
        return ResponseEntity.ok().body(cartaoDeCredito);
    }

    @PutMapping("/{idCiclista}")
    @ApiOperation(value="Altera os dados de um cartao de crédito existente")
    public ResponseEntity<CartaoDeCredito> alteraCartaoDeCredito(@PathVariable int idCiclista, @RequestBody CartaoDeCreditoDTO dadosAlteracaoCartaoDeCredito) {
        return ResponseEntity.ok().body(this.service.alteraCartaoDeCredito(idCiclista, dadosAlteracaoCartaoDeCredito));
    }
}
