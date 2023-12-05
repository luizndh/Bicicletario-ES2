package com.aluguel.service;

import com.aluguel.dto.FuncionarioDTO;
import com.aluguel.dto.NovoFuncionarioDTO;
import com.aluguel.model.Funcionario;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aluguel.model.Funcionario.funcionarios;;

@Service
public class FuncionarioService {
    public void restauraDados() {
        funcionarios.clear();

        Funcionario funcionario = new Funcionario(new NovoFuncionarioDTO("123", "123", "employee@example.com", "Beltrano", "25", "Reparador", "99999999999"));
        funcionarios.add(funcionario);
        alteraFuncionario(funcionario.getMatricula(), new FuncionarioDTO("12345", "123", "123", "employee@example.com", "Beltrano", "25", "Reparador", "99999999999"));
    }

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

    public Funcionario alteraFuncionario(String matriculaFuncionario, FuncionarioDTO dadosAlteracaoFuncionario) {
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

