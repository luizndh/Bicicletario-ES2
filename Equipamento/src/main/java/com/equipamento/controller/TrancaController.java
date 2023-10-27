package com.equipamento.controller;

import com.equipamento.DTO.TrancaDTO;
import com.equipamento.model.Tranca;
import com.equipamento.servico.TrancaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
        System.out.println("CHEGUEI NO CONTROLLER DE TRANCA");
        Tranca t = this.service.recuperaTrancaPorId(idTranca);
        return ResponseEntity.ok().body(t);
    }

    @GetMapping("/")
    @ApiOperation(value="Retorna todas as trancas cadastradas no sistema")
    public ResponseEntity<List<Tranca>> recuperaTrancas() {
        return ResponseEntity.ok().body(this.service.recuperaTrancas());
    }

    @PostMapping("/")
    @ApiOperation(value="Cadastra uma nova tranca no sistema")
    public ResponseEntity<Tranca> cadastraTranca(@RequestBody TrancaDTO dadosCadastroTranca) {
        return ResponseEntity.ok().body(this.service.cadastraTranca(dadosCadastroTranca));
    }

    @PutMapping("/{idTranca")
    @ApiOperation(value="Altera os dados de uma tranca existente")
    public ResponseEntity<Tranca> alteraTranca(@PathVariable int idTranca, @RequestBody TrancaDTO dadosAlteracaoTranca) {
        return ResponseEntity.ok().body(this.service.alteraTranca(idTranca, dadosAlteracaoTranca));
    }

    @DeleteMapping("/{idTranca")
    @ApiOperation(value="Exclui uma tranca do sistema")
    public ResponseEntity<?> excluiTranca(@PathVariable int idTranca) {
        this.service.excluiTranca(idTranca);
        return ResponseEntity.ok().build();
    }
}
