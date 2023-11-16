package com.equipamento.controller;

import com.equipamento.dto.BicicletaDTO;
import com.equipamento.dto.InclusaoBicicletaDTO;
import com.equipamento.dto.RetiradaBicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.model.Bicicleta.StatusBicicleta;
import com.equipamento.servico.BicicletaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bicicleta")
@Api(value="API para requisicoes relacionadas a bicicleta")
public class BicicletaController {

    @Autowired
    private BicicletaService service;

    @GetMapping("/{idBicicleta}")
    @ApiOperation(value="Retorna uma bicicleta com o id passado")
    public ResponseEntity<Bicicleta> recuperaBicicletaPorId(@PathVariable int idBicicleta) {
        Bicicleta t = this.service.recuperaBicicletaPorId(idBicicleta);
        return ResponseEntity.ok().body(t);
    }

    @GetMapping("")
    @ApiOperation(value="Retorna todas as bicicletas cadastradas no sistema")
    public ResponseEntity<List<Bicicleta>> recuperaBicicletas() {
        return ResponseEntity.ok().body(this.service.recuperaBicicletas());
    }

    @PostMapping(value = "", consumes = "application/json")
    @ApiOperation(value="Cadastra uma nova bicicleta no sistema")
    public ResponseEntity<Bicicleta> cadastraBicicleta(@RequestBody BicicletaDTO dadosCadastroBicicleta) {
        return ResponseEntity.ok().body(this.service.cadastraBicicleta(dadosCadastroBicicleta));
    }

    @PutMapping(value = "/{idBicicleta}", consumes = "application/json")
    @ApiOperation(value="Altera os dados de uma bicicleta existente")
    public ResponseEntity<Bicicleta> alteraBicicleta(@PathVariable int idBicicleta, @RequestBody BicicletaDTO dadosAlteracaoBicicleta) {
        return ResponseEntity.ok().body(this.service.alteraBicicleta(idBicicleta, dadosAlteracaoBicicleta));
    }

    @DeleteMapping("/{idBicicleta}")
    @ApiOperation(value="Exclui uma bicicleta do sistema")
    public ResponseEntity<?> excluiBicicleta(@PathVariable int idBicicleta) {
        this.service.excluiBicicleta(idBicicleta);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idBicicleta}/status/{acao}")
    @ApiOperation(value="Altera o status de uma bicicleta")
    public ResponseEntity<Bicicleta> alteraStatusBicicleta(@PathVariable int idBicicleta, @PathVariable StatusBicicleta acao) {
        return ResponseEntity.ok().body(this.service.alteraStatusBicicleta(idBicicleta, acao));
    }

    @PostMapping(value = "/integrarNaRede", consumes = "application/json")
    @ApiOperation(value="Coloca uma bicicleta nova ou retornando de reparo de volta na rede de totens")
    public ResponseEntity<Void> integraNaRede(@RequestBody InclusaoBicicletaDTO dadosInclusao) {
        this.service.integrarNaRede(dadosInclusao);
        return ResponseEntity.status(200).build();
    }

    @PostMapping(value = "/retirarDaRede", consumes = "application/json")
    @ApiOperation(value="Retira uma bicicleta para reparo ou aposentadoria")
    public ResponseEntity<Void> retiraDaRede(@RequestBody RetiradaBicicletaDTO dadosRetirada) {
        this.service.retirarDaRede(dadosRetirada);
        return ResponseEntity.ok().build();
    }
}
