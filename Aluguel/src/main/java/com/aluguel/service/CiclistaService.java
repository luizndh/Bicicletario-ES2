package com.aluguel.service;

import com.aluguel.Integracoes;
import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.CartaoDeCreditoDTO;
import com.aluguel.dto.CiclistaDTO;
import com.aluguel.dto.NovoCiclistaDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.CartaoDeCredito;
import com.aluguel.model.Ciclista;
import com.aluguel.model.Passaporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.aluguel.model.Aluguel.alugueis;
import static com.aluguel.model.CartaoDeCredito.cartoesDeCreditos;
import static com.aluguel.model.Ciclista.ciclistas;

@Service
public class CiclistaService {

    @Autowired
    Integracoes integracoes;

    public void restauraDados() {
        ciclistas.clear();
        cartoesDeCreditos.clear();

        ciclistas.add(new Ciclista(new NovoCiclistaDTO("Fulano Beltrano", "2021-05-02", "78804034009", new Passaporte("", "", "") , "Brasileiro", "user@example.com", "ABC123")));
        ciclistas.add(new Ciclista(new NovoCiclistaDTO("Fulano Beltrano", "2021-05-02", "43943488039", new Passaporte("", "", "") , "Brasileiro", "user2@example.com", "ABC123")));
        ciclistas.add(new Ciclista(new NovoCiclistaDTO("Fulano Beltrano", "2021-05-02", "10243164084", new Passaporte("", "", "") , "Brasileiro", "user3@example.com", "ABC123")));
        ciclistas.add(new Ciclista(new NovoCiclistaDTO("Fulano Beltrano", "2021-05-02", "30880150017", new Passaporte("", "", "") , "Brasileiro", "user4@example.com", "ABC123")));
    }

    public Ciclista recuperaCiclistaPorId(int idCiclista) {
        for (Ciclista ciclista : ciclistas) {
            if (ciclista.getId() == idCiclista) {
                return ciclista;
            }
        }
        throw new NoSuchElementException("O ciclista com id " + idCiclista + " não existe");
    }

    public List<Ciclista> recuperaCiclistas() { return ciclistas; }

    public boolean verificaEmail(String email) {
        String padraoEmail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern padrao = java.util.regex.Pattern.compile(padraoEmail);
        java.util.regex.Matcher matcher = padrao.matcher(email);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("Email inválido");
        }

        if(!ciclistas.isEmpty()) {
            for (Ciclista ciclista : ciclistas) {
                if (ciclista.getEmail().equals(email)) {
                    return true;
                }
            }
        }
        throw new IllegalArgumentException("Já existe um ciclista com o email " + email + " cadastrado");
    }

    public Ciclista cadastraCiclista(NovoCiclistaDTO dadosCadastroCiclista, CartaoDeCreditoDTO cartaoDeCredito) {
        
        //validaNacionalidade(dadosCadastroCiclista.cpf(), dadosCadastroCiclista.passaporte().getNumero(), dadosCadastroCiclista.nacionalidade());

        integracoes.validaCartaoDeCredito(cartaoDeCredito);
        
        integracoes.enviaEmail(dadosCadastroCiclista.email(), "Bem vindo ao Aluguel de Bicicletas", 
               "Olá " + dadosCadastroCiclista.nome() + ",\n\n" +
               "Seu cadastro foi realizado com sucesso!\n\n" +
               "Agora você já pode alugar bicicletas e aproveitar o melhor da cidade.\n\n" +
               "Atenciosamente,\n" +
               "Equipe Aluguel de Bicicletas");

        Ciclista ciclista = new Ciclista(dadosCadastroCiclista);
        ciclistas.add(ciclista);
        CartaoDeCredito cartaoDeCreditoCadastrado = new CartaoDeCredito(cartaoDeCredito);
        cartoesDeCreditos.add(cartaoDeCreditoCadastrado);
        return ciclista;
    }

    public Ciclista ativaCiclista(int idCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        if (!ciclista.isAtivo()) {
            ciclista.ativaCiclista();
            return ciclista;
        }

        throw new NoSuchElementException("O ciclista com id " + idCiclista + " não existe");
    }

    public boolean verificaSeCiclistaPodeAlugar(int idCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        if (!ciclista.isAtivo()) {
            throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não está ativo");
        } else if (recuperaCiclistaPorIdEmAluguel(idCiclista) != null) {
            Aluguel aluguel = AluguelService.recuperaAluguelPorIdCiclista(idCiclista);

            integracoes.enviaEmail(ciclista.getEmail() , "Aluguel em andamento" ,
                    "Olá " + ciclista.getNome() + ",\n\n" +
                    "Você já tem um aluguel em andamento. \n" +
                    "Seguem os dados do seu aluguel: \n\n" +
                    "Numero de bicicleta: " + aluguel.getBicicleta() + "\n" +
                    "hora de inicio: " + aluguel.getHoraInicio() + "\n" +
                    "tranca de retirada: " + aluguel.getTrancaInicio() + "\n\n" +
                    "Se você não está usando a bicicleta, por favor, entre em contato com a gente.\n\n" +
                    "Atenciosamente,\n" +
                    "Equipe Aluguel de Bicicletas");
            
            throw new IllegalArgumentException("Ciclista já tem aluguel em andamento");
        }
        return true;
    }
    
    public Ciclista recuperaCiclistaPorIdEmAluguel(int idCiclista) {
        if (!alugueis.isEmpty()) {
            for (Aluguel aluguel : alugueis) {
                if (aluguel.getCiclista() == idCiclista) {
                    return recuperaCiclistaPorId(idCiclista);
                }
            }
        }
        throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não está em aluguel");
    }

    public Ciclista alteraCiclista(int idCiclista, CiclistaDTO dadosAlteracaoCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        //validaNacionalidade(ciclista.getCpf(), ciclista.getPassaporte().getNumero(), dadosAlteracaoCiclista.nacionalidade());

        integracoes.enviaEmail(ciclista.getEmail(), "Dados alterados no Aluguel de Bicicletas",
               "Olá " + ciclista.getNome() + ",\n\n" +
               "Seus dados foram alterados com sucesso!\n\n" +
               "Atenciosamente,\n" +
               "Equipe Aluguel de Bicicletas");

        ciclista.atualizaCiclista(dadosAlteracaoCiclista);
        return ciclista;
    }

    public BicicletaDTO recuperaBicicletaAlugada (int idCiclista) {
        Aluguel aluguel = AluguelService.recuperaAluguelPorIdCiclista(idCiclista);
        
        return integracoes.recuperaBicicletaPorId(aluguel.getBicicleta());
    }

    // private void validaNacionalidade(String cpf, String passaporte, String nacionalidade) {
    //     if (cpf.isEmpty() && nacionalidade.equals("brasileiro")) {
    //         throw new IllegalArgumentException("O CPF é obrigatório para brasileiros");
    //     } 
    //     if (passaporte.isEmpty() && nacionalidade.equals("estrangeiro")) {
    //         throw new IllegalArgumentException("O passaporte é obrigatório para estrangeiros");
    //     }
    // }
}

