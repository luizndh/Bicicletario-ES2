package com.aluguel.service;

import com.aluguel.dto.NovoFuncionarioDTO;
import com.aluguel.model.Funcionario;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aluguel.model.Funcionario.funcionarios;;

@Service
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

    public Funcionario cadastraFuncionario(NovoFuncionarioDTO dadosCadastroFuncionario) {
        Funcionario funcionario = new Funcionario(dadosCadastroFuncionario);
        funcionarios.add(funcionario);
        return funcionario;
    }

    public Funcionario alteraFuncionario(String matriculaFuncionario, NovoFuncionarioDTO dadosAlteracaoFuncionario) {
        Funcionario funcionario = recuperaFuncionarioPorMatricula(matriculaFuncionario);
        funcionario.atualizaFuncionario(dadosAlteracaoFuncionario);
        return funcionario;
    }

    public boolean excluiFuncionario(String matriculaFuncionario) {
        Funcionario funcionario = recuperaFuncionarioPorMatricula(matriculaFuncionario);
        funcionarios.remove(funcionario);
        return true;
    }
}

