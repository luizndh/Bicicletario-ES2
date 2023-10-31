package com.equipamento.controller;

import com.equipamento.DTO.BicicletaDTO;
import com.equipamento.model.Bicicleta;
import com.equipamento.servico.BicicletaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    @ApiOperation(value="Retorna todas as bicicletas cadastradas no sistema")
    public ResponseEntity<List<Bicicleta>> recuperaBicicletas() {
        return ResponseEntity.ok().body(this.service.recuperaBicicletas());
    }

    @PostMapping("/")
    @ApiOperation(value="Cadastra uma nova bicicleta no sistema")
    public ResponseEntity<Bicicleta> cadastraBicicleta(@RequestBody BicicletaDTO dadosCadastroBicicleta) {
        return ResponseEntity.ok().body(this.service.cadastraBicicleta(dadosCadastroBicicleta));
    }

    @PutMapping("/{idBicicleta}")
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
}
