package com.aluguel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguel.DTO.BicicletaDTO;
import com.aluguel.DTO.CiclistaDTO;
import com.aluguel.model.Ciclista;
import com.aluguel.service.CiclistaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ciclista")
@Api(value="API para requisicoes relacionadas ao ciclista")
public class CiclistaController {

    @Autowired
    private CiclistaService service;

    @GetMapping("/{idCiclista}")
    @ApiOperation(value="Retorna um ciclista com o id passado")
    public ResponseEntity<Ciclista> recuperaCiclistaPorId(@PathVariable int idCiclista) {
        Ciclista ciclista = this.service.recuperaCiclistaPorId(idCiclista);
        return ResponseEntity.ok().body(ciclista);
    }

    @GetMapping("/existeEmail/{email}")
    @ApiOperation(value="Verifica se existe um ciclista com o email passado")
    public ResponseEntity<Boolean> verificaEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(this.service.verificaEmail(email));
    }

    @PostMapping("/")
    @ApiOperation(value="Cadastra um novo ciclista no sistema")
    public ResponseEntity<Ciclista> cadastraCiclista(@RequestBody CiclistaDTO dadosCadastroCiclista) {
        return ResponseEntity.ok().body(this.service.cadastraCiclista(dadosCadastroCiclista));
    }

    @GetMapping("/{idCiclista}/permiteAluguel")
    @ApiOperation(value="Verifica se um ciclista pode alugar uma bicicleta")
    public ResponseEntity<Boolean> verificaSeCiclistaPodeAlugar(@PathVariable int idCiclista) {
        return ResponseEntity.ok().body(this.service.verificaSeCiclistaPodeAlugar(idCiclista));
    }

    @PostMapping("/{idCiclista}/ativar")
    @ApiOperation(value="Ativa um ciclista existente")
    public ResponseEntity<Ciclista> ativaCiclista(@PathVariable int idCiclista) {
        return ResponseEntity.ok().body(this.service.ativaCiclista(idCiclista));
    }

    @PutMapping("/{idCiclista}")
    @ApiOperation(value="Altera os dados de um ciclista existente")
    public ResponseEntity<Ciclista> alteraCiclista(@PathVariable int idCiclista, @RequestBody CiclistaDTO dadosAlteracaoCiclista) {
        return ResponseEntity.ok().body(this.service.alteraCiclista(idCiclista, dadosAlteracaoCiclista));
    }

    @GetMapping("/ciclista/{idCiclista}/bicicletaAlugada")
    @ApiOperation(value="Retorna a bicicleta alugada por um ciclista")
    public ResponseEntity<BicicletaDTO> recuperaBicicletaAlugada(@PathVariable int idCiclista) {
        return ResponseEntity.ok().body(this.service.recuperaBicicletaAlugada(idCiclista));
    }
}
