package com.equipamento.controller;

import com.equipamento.DTO.InclusaoTrancaDTO;
import com.equipamento.DTO.RetiradaTrancaDTO;
import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Tranca;
import com.equipamento.model.Tranca.StatusTranca;
import com.equipamento.servico.TrancaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tranca")
@Api(value="API para requisicoes relacionadas a tranca")
public class TrancaController {

    @Autowired
    private TrancaService service;

    @GetMapping("/{idTranca}")
    @ApiOperation(value="Retorna uma tranca com o id passado")
    public ResponseEntity<Tranca> recuperaTrancaPorId(@PathVariable int idTranca) {
        Tranca t = this.service.recuperaTrancaPorId(idTranca);
        return ResponseEntity.ok().body(t);
    }

    @GetMapping("")
    @ApiOperation(value="Retorna todas as trancas cadastradas no sistema")
    public ResponseEntity<List<Tranca>> recuperaTrancas() {
        return ResponseEntity.ok().body(this.service.recuperaTrancas());
    }

    @PostMapping(value = "", consumes = "application/json")
    @ApiOperation(value="Cadastra uma nova tranca no sistema")
    public ResponseEntity<Tranca> cadastraTranca(@RequestBody TrancaDTO dadosCadastroTranca) {
        return ResponseEntity.ok().body(this.service.cadastraTranca(dadosCadastroTranca));
    }

    @PutMapping(value = "/{idTranca}", consumes = "application/json")
    @ApiOperation(value="Altera os dados de uma tranca existente")
    public ResponseEntity<Tranca> alteraTranca(@PathVariable int idTranca, @RequestBody TrancaDTO dadosAlteracaoTranca) {
        return ResponseEntity.ok().body(this.service.alteraTranca(idTranca, dadosAlteracaoTranca));
    }

    @DeleteMapping("/{idTranca}")
    @ApiOperation(value="Exclui uma tranca do sistema")
    public ResponseEntity excluiTranca(@PathVariable int idTranca) {
        this.service.excluiTranca(idTranca);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idTranca}/status/{acao}")
    @ApiOperation(value="Altera o status de uma tranca")
    public ResponseEntity<Tranca> alteraStatusTranca(@PathVariable int idTranca, @PathVariable StatusTranca acao) {
        return ResponseEntity.ok().body(this.service.alteraStatusTranca(idTranca, acao));
    }

    @PostMapping(value = "/{idTranca}/trancar", consumes = "application/json")
    @ApiOperation(value="Realiza o trancamento da tranca alterando o status da mesma de acordo. " +
            "Caso receba o id da bicicleta no corpo do post também altera o status da mesma e associa a tranca à bicicleta")
    public ResponseEntity<Tranca> realizaTrancamento(@PathVariable int idTranca, @RequestBody int idBicicleta) {
        return ResponseEntity.ok().body(this.service.realizarTrancamento(idTranca, idBicicleta));
    }

    @PostMapping(value = "/{idTranca}/destrancar", consumes = "application/json")
    @ApiOperation(value="Realiza o destrancamento da tranca alterando o status da mesma de acordo. " +
             "Caso receba o id da bicicleta no corpo do post também altera o status da mesma e desassocia a tranca à bicicleta.")
    public ResponseEntity<Tranca> realizaDestrancamento(@PathVariable int idTranca, @RequestBody int idBicicleta) {
        return ResponseEntity.ok().body(this.service.realizarDestrancamento(idTranca, idBicicleta));
    }

    @PostMapping(value = "/integrarNaRede", consumes = "application/json")
    @ApiOperation(value="Colocar uma tranca nova ou retornando de reparo de volta na rede de totens")
    public ResponseEntity integrarNaRede(@RequestBody InclusaoTrancaDTO dadosInclusao) {
        this.service.integrarNaRede(dadosInclusao);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "retirarDaRede", consumes = "application/json")
    @ApiOperation(value="Retirar uma tranca para aposendatoria ou reparo")
    public ResponseEntity retirarDaRede(@RequestBody RetiradaTrancaDTO dadosRetirada) {
        this.service.retirarDaRede(dadosRetirada);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idTranca}/bicicleta")
    @ApiOperation(value="Obter bicicleta na tranca")
    public ResponseEntity<Bicicleta> obterBicicletaNaTranca(@PathVariable int idTranca) {
        return ResponseEntity.ok().body(this.service.obterBicicletaNaTranca(idTranca));
    }
}
