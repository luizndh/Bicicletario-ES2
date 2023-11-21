package com.equipamento.controller;

import com.equipamento.dto.TotemDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Totem;
import com.equipamento.model.Tranca;
import com.equipamento.servico.TotemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/totem")
@Api(value="API para requisicoes relacionadas ao totem")
public class TotemController {

    @Autowired
    private TotemService service;

    @GetMapping("")
    @ApiOperation(value="Retorna todos os totems cadastrados no sistema")
    public ResponseEntity<List<Totem>> recuperaTotens() {
        return ResponseEntity.ok().body(this.service.recuperaTotens());
    }

    @PostMapping(value = "", consumes = "application/json")
    @ApiOperation(value="Cadastra um novo totem no sistema")
    public ResponseEntity<Totem> cadastraTotem(@RequestBody TotemDTO dadosCadastroTotem) {
        return ResponseEntity.ok().body(this.service.cadastraTotem(dadosCadastroTotem));
    }

    @PutMapping(value = "/{idTotem}", consumes = "application/json")
    @ApiOperation(value="Altera os dados de um totem existente")
    public ResponseEntity<Totem> alteraTotem(@PathVariable int idTotem, @RequestBody TotemDTO dadosAlteracaoTotem) {
        return ResponseEntity.ok().body(this.service.alteraTotem(idTotem, dadosAlteracaoTotem));
    }

    @DeleteMapping("/{idTotem}")
    @ApiOperation(value="Exclui um totem do sistema")
    public ResponseEntity<Void> excluiTotem(@PathVariable int idTotem) {
        this.service.excluiTotem(idTotem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idTotem}/trancas")
    @ApiOperation(value="Retorna todas as trancas cadastradas no totem")
    public ResponseEntity<List<Tranca>> recuperaTrancasDoTotem(@PathVariable int idTotem) {
        return ResponseEntity.ok().body(this.service.recuperaTrancasDoTotem(idTotem));
    }

    @GetMapping("/{idTotem}/bicicletas")
    @ApiOperation(value="Retorna todas as bicicletas cadastradas no totem")
    public ResponseEntity<List<Bicicleta>> recuperaBicicletasDoTotem(@PathVariable int idTotem) {
        return ResponseEntity.ok().body(this.service.recuperaBicicletasDoTotem(idTotem));
    }
}
