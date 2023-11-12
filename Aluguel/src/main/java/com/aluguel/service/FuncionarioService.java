package com.aluguel.service;

import com.aluguel.DTO.FuncionarioDTO;
import com.aluguel.model.Funcionario;

import java.util.List;

import static com.aluguel.model.Funcionario.funcionarios;;

public class FuncionarioService {
    public Funcionario recuperaFuncionarioPorMatricula(String matriculaFuncionario) {
        if(!funcionarios.isEmpty()) {
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getMatricula() == matriculaFuncionario) {
                    return funcionario;
                }
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
        if(!funcionarios.isEmpty()) {
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getMatricula() == matriculaFuncionario) {
                    funcionario.atualizaFuncionario(dadosAlteracaoFuncionario);
                    return funcionario;
                }
            }
        }
        throw new IllegalArgumentException("O funcionario com matricula " + matriculaFuncionario + " não existe");
    }

    public void excluiFuncionario(String matriculaFuncionario) {
        if(!funcionarios.isEmpty()) {
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getMatricula() == matriculaFuncionario) {
                    funcionarios.remove(funcionario);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("O funcionario com matricula " + matriculaFuncionario + " não existe");
    }
}

