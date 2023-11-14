package com.aluguel.service;

import com.aluguel.DTO.FuncionarioDTO;
import com.aluguel.model.Funcionario;

import java.util.List;

import static com.aluguel.model.Funcionario.funcionarios;;

public class FuncionarioService {
    public Funcionario recuperaFuncionarioPorMatricula(String matriculaFuncionario) {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getMatricula().equals(matriculaFuncionario)) {
                return funcionario;
            }
        }
        throw new IllegalArgumentException("O funcionario com matricula " + matriculaFuncionario + " não existe");
    }

    public List<Funcionario> recuperaFuncionarios() { return funcionarios; }

    public Funcionario cadastraFuncionario(FuncionarioDTO dadosCadastroFuncionario) {
        Funcionario funcionario = new Funcionario(dadosCadastroFuncionario);
        funcionarios.add(funcionario);
        return funcionario;
    }

    public Funcionario alteraFuncionario(String matriculaFuncionario, FuncionarioDTO dadosAlteracaoFuncionario) {
        Funcionario funcionario = recuperaFuncionarioPorMatricula(matriculaFuncionario);
        funcionario.atualizaFuncionario(dadosAlteracaoFuncionario);
        return funcionario;
    }

    public void excluiFuncionario(String matriculaFuncionario) {
        Funcionario funcionario = recuperaFuncionarioPorMatricula(matriculaFuncionario);
        funcionarios.remove(funcionario);
        return;
    }
}

