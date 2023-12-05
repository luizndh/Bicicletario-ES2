package com.aluguel.service;

import com.aluguel.dto.FuncionarioDTO;
import com.aluguel.dto.NovoFuncionarioDTO;
import com.aluguel.model.Funcionario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class FuncionarioServiceTest {
    @Mock
    private FuncionarioService funcionarioService;

    @Mock
    private Funcionario funcionario;

    @Mock
    private List<Funcionario> funcionarios;

    @BeforeEach
    void setUp() {
        // Arrange

        NovoFuncionarioDTO funcionarioDTO1 = new NovoFuncionarioDTO("123", "123", "kleber@kalango.com", "kleber", "5", "muito jovem aprendiz", "333.222.111-00");
        NovoFuncionarioDTO funcionarioDTO2 = new NovoFuncionarioDTO("42424", "42424", "mia@miojo.com", "mia", "23", "entreter", "696.969.696-96");
        NovoFuncionarioDTO funcionarioDTO3 = new NovoFuncionarioDTO("666", "666", "tulio@tijolo.com", "tulio", "45", "ter 3 empregos", "666.666.666-66");

        funcionarios = new ArrayList<Funcionario>();
        funcionarios.add(new Funcionario(funcionarioDTO1));
        funcionarios.add(new Funcionario(funcionarioDTO2));
        funcionarios.add(new Funcionario(funcionarioDTO3));
    }

    @Test
    void testRecuperaFuncionarioPorMatricula() {
        // Arrange
        if (Funcionario.funcionarios.isEmpty()) {
            return;
        }
        String matricula = Funcionario.funcionarios.get(0).getMatricula();
        when(funcionarioService.recuperaFuncionarioPorMatricula(matricula)).then(invocation -> {
            funcionario = funcionarios.get((Integer.parseInt(matricula) - 1));
            return funcionario;
        });
        
        // Act
        Funcionario funcionario = funcionarioService.recuperaFuncionarioPorMatricula(matricula);

        // Assert
        assertNotNull(funcionario);
        assertEquals(matricula, funcionario.getMatricula());
    }

    @Test
    void testRecuperaFuncionarios() {
        // Arrange
        when(funcionarioService.recuperaFuncionarios()).thenReturn(funcionarios);

        // Act
        List<Funcionario> funcionariosRetornados = funcionarioService.recuperaFuncionarios();

        // Assert
        assertNotNull(funcionariosRetornados);
        assertEquals(3, funcionariosRetornados.size());
    }

    @Test
    void testCadastraFuncionario() {
        // Arrange
        NovoFuncionarioDTO dadosCadastroFuncionario = new NovoFuncionarioDTO("faiscaGratuita", "faiscaGratuita", "frifas@boyahh.com", "joãozin", "14", "fumante", "###.###.###-##");
        when(funcionarioService.cadastraFuncionario(dadosCadastroFuncionario)).then(invocation -> {
            Funcionario novoFuncionario = new Funcionario(dadosCadastroFuncionario);
            funcionarios.add(novoFuncionario);
            return novoFuncionario;
        });

        // Act
        Funcionario funcionario = funcionarioService.cadastraFuncionario(dadosCadastroFuncionario);

        // Assert
        assertNotNull(funcionario);
        assertEquals("joãozin", funcionario.getNome());
        assertTrue(funcionarios.contains(funcionario));
        assertEquals(4, funcionarios.size());
    }

    @Test
    void testAlteraFuncionario() {
        // Arrange
        FuncionarioDTO dadosAlteracaoFuncionario = new FuncionarioDTO("1", "omg123", "omg123", "mikaGuei@nemLimpei.com", "Mika Guei", "19", "cagante", "823.794.383-12");
        when(funcionarioService.alteraFuncionario("1", dadosAlteracaoFuncionario)).then(invocation -> {
            Funcionario funcionario = funcionarios.get(0);
            funcionario.atualizaFuncionario(dadosAlteracaoFuncionario);
            return funcionario;
        });

        // Act
        Funcionario funcionario = funcionarioService.alteraFuncionario("1", dadosAlteracaoFuncionario);

        // Assert
        assertNotNull(funcionario);
        assertEquals("omg123", funcionario.getSenha());
        assertEquals("omg123", funcionario.getConfirmacaoSenha());
        assertEquals("mikaGuei@nemLimpei.com", funcionario.getEmail());
        assertEquals("Mika Guei", funcionario.getNome());
        assertEquals(19, funcionario.getIdade());
        assertEquals("cagante", funcionario.getFuncao());
        assertEquals("823.794.383-12", funcionario.getCpf());
        assertEquals(3, funcionarios.size());
    }

    @Test
    void testExcluiFuncionario() {
        // Arrange
        if (Funcionario.funcionarios.isEmpty()) {
            return;
        }
        String matricula = Funcionario.funcionarios.get(funcionarios.size()-1).getMatricula();
        when(funcionarioService.recuperaFuncionarioPorMatricula(matricula)).thenReturn(funcionarios.get((Integer.parseInt(matricula) - 1)));
        doAnswer(invocation -> {
            Funcionario funcionario = funcionarios.get(funcionarios.size()-1);
            funcionarios.remove(funcionario);
            return null;
        }).when(funcionarioService).excluiFuncionario(matricula);
     
        // Act
        funcionarioService.excluiFuncionario(matricula);
        Funcionario funcionario = funcionarioService.recuperaFuncionarioPorMatricula(matricula);

        // Assert
        assertNull(funcionario);
    }
}