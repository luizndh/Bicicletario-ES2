package com.aluguel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aluguel.dto.FuncionarioDTO;
import com.aluguel.model.Funcionario;
import com.aluguel.service.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/funcionario")
@Api(value="API para requisicoes relacionadas ao funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @GetMapping("/{idFuncionario}")
    @ApiOperation(value="Retorna um funcionario com o id passado")
    public ResponseEntity<Funcionario> recuperaFuncionarioPorId(@PathVariable String matriculaFuncionario) {
        Funcionario funcionario = this.service.recuperaFuncionarioPorMatricula(matriculaFuncionario);
        return ResponseEntity.ok().body(funcionario);
    }

    @GetMapping("/")
    @ApiOperation(value="Retorna todos os funcionarios cadastrados no sistema")
    public ResponseEntity<List<Funcionario>> recuperaFuncionarios() {
        return ResponseEntity.ok().body(this.service.recuperaFuncionarios());
    }

    @PostMapping("/")
    @ApiOperation(value="Cadastra um novo funcionario no sistema")
    public ResponseEntity<Funcionario> cadastraFuncionario(@RequestBody FuncionarioDTO dadosCadastroFuncionario) {
        return ResponseEntity.ok().body(this.service.cadastraFuncionario(dadosCadastroFuncionario));
    }

    @PutMapping("/{matriculaFuncionario}")
    @ApiOperation(value="Altera os dados de um funcionario existente")
    public ResponseEntity<Funcionario> alteraFuncionario(@PathVariable String matriculaFuncionario, @RequestBody FuncionarioDTO dadosAlteracaoFuncionario) {
        return ResponseEntity.ok().body(this.service.alteraFuncionario(matriculaFuncionario, dadosAlteracaoFuncionario));
    }

    @DeleteMapping("/{matriculaFuncionario}")
    @ApiOperation(value="Exclui um funcionario do sistema")
    public ResponseEntity excluiBicicleta(@PathVariable String matriculaFuncionario) {
        this.service.excluiFuncionario(matriculaFuncionario);
        return ResponseEntity.ok().build();
    }
}
