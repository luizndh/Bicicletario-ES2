package com.aluguel.model;

import com.aluguel.dto.FuncionarioDTO;
import com.aluguel.dto.NovoFuncionarioDTO;

import java.util.ArrayList;
import java.util.List;

public class Funcionario {

    private String matricula;
    private String senha;
    private String confirmacaoSenha;
    private String email;
    private String nome;
    private int idade;
    private String funcao;
    private String cpf;

    public static final List<Funcionario> funcionarios = new ArrayList<>();

    public Funcionario(NovoFuncionarioDTO dadosCadastroFuncionario) {
        this.matricula = String.valueOf(funcionarios.size() + 1);
        this.senha = dadosCadastroFuncionario.senha();
        this.confirmacaoSenha = dadosCadastroFuncionario.confirmacaoSenha();
        this.email = dadosCadastroFuncionario.email();
        this.nome = dadosCadastroFuncionario.nome();
        this.idade = Integer.valueOf(dadosCadastroFuncionario.idade());
        this.funcao = dadosCadastroFuncionario.funcao();
        this.cpf = dadosCadastroFuncionario.cpf();
    }

    public void atualizaFuncionario(FuncionarioDTO dadosAlteracaoFuncionario) {
        this.matricula = dadosAlteracaoFuncionario.matricula();
        this.senha = dadosAlteracaoFuncionario.senha();
        this.confirmacaoSenha = dadosAlteracaoFuncionario.confirmacaoSenha();
        this.email = dadosAlteracaoFuncionario.email();
        this.nome = dadosAlteracaoFuncionario.nome();
        this.idade = Integer.valueOf(dadosAlteracaoFuncionario.idade());
        this.funcao = dadosAlteracaoFuncionario.funcao();
        this.cpf = dadosAlteracaoFuncionario.cpf();
    }

    public String getMatricula() {
        return this.matricula;
    }
    
    public String getSenha() {
        return this.senha;
    }

    public String getConfirmacaoSenha() {
        return this.confirmacaoSenha;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public String getCpf() {
        return this.cpf;
    }
}
