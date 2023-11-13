package com.externo.controller;

import com.externo.DTO.CartaoDeCreditoDTO;
import com.externo.servico.CartaoDeCreditoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validaCartaoDeCredito")
@Api(value="API para validar o cartao de credito")
public class CartaoDeCreditoController {

    @Autowired
    private CartaoDeCreditoService service;

    @PostMapping("")
    @ApiOperation(value="Valida o cartão de crédito")
    public ResponseEntity<Boolean> validaCartaoDeCredito(@RequestBody CartaoDeCreditoDTO dadosCadastroCartao) {
        return ResponseEntity.ok().body(this.service.validaCartaoDeCredito(dadosCadastroCartao));
    }

}
