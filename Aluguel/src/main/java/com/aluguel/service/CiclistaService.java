package com.aluguel.service;

import com.aluguel.dto.BicicletaDTO;
import com.aluguel.dto.CiclistaDTO;
import com.aluguel.dto.NovoCiclistaDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.Ciclista;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static com.aluguel.model.Aluguel.alugueis;
import static com.aluguel.model.Ciclista.ciclistas;

@Service
public class CiclistaService {

    final String CICLISTA_EXCEPTION_1 = "O ciclista com id ";
    final String CICLISTA_EXCEPTION_2 = " não existe";

    public Ciclista recuperaCiclistaPorId(int idCiclista) {
        for (Ciclista ciclista : ciclistas) {
            if (ciclista.getId() == idCiclista) {
                return ciclista;
            }
        }
        throw new NoSuchElementException(CICLISTA_EXCEPTION_1 + idCiclista + CICLISTA_EXCEPTION_2);
    }

    public List<Ciclista> recuperaCiclistas() { return ciclistas; }

    public boolean verificaEmail(String email) {
        String padraoEmail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern padrao = java.util.regex.Pattern.compile(padraoEmail);
        java.util.regex.Matcher matcher = padrao.matcher(email);

        if (!matcher.matches()) {
            
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

    // TODO verificar senhas iguais
    public Ciclista cadastraCiclista(NovoCiclistaDTO dadosCadastroCiclista) {
        
        validaNacionalidade(dadosCadastroCiclista.cpf(), dadosCadastroCiclista.passaporte().getNumero(), dadosCadastroCiclista.nacionalidade());

        //if (false) { // TODO validar cartão junto a administradora CC
        //    return null;
        //}
        
        // TODO tenta enviar email e se não conseguir, indica o erro. Se der erro no envio do email, não salva o ciclista.
        ///enviarEmail(ciclista.getEmail(), "Bem vindo ao Aluguel de Bicicletas", 
        //        "Olá " + ciclista.getNome() + ",\n\n" +
        //        "Seu cadastro foi realizado com sucesso!\n\n" +
        //        "Agora você já pode alugar bicicletas e aproveitar o melhor da cidade.\n\n" +
        //        "Atenciosamente,\n" +
        //        "Equipe Aluguel de Bicicletas");

        Ciclista ciclista = new Ciclista(dadosCadastroCiclista);
        ciclistas.add(ciclista);
        return ciclista;
    }

    // Onde "O sistema registre a data/hora da confirmação."?
    public Ciclista ativaCiclista(int idCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        if (!ciclista.isAtivo()) {
            ciclista.ativaCiclista();
            return ciclista;
        }

        throw new NoSuchElementException(CICLISTA_EXCEPTION_1 + idCiclista + CICLISTA_EXCEPTION_2);
    }

    public boolean verificaSeCiclistaPodeAlugar(int idCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        if (!ciclista.isAtivo()) {
            throw new IllegalArgumentException(CICLISTA_EXCEPTION_1 + idCiclista + " não está ativo");
        } else if (recuperaCiclistaPorIdEmAluguel(idCiclista) != null) {
            // TODO: envia email para o ciclista com os dados do aluguel em andamento
            // /enviarEmail (ciclista.getEmail() , "Aluguel em andamento" ,
            //         "Olá " + ciclista.getNome() + ",\n\n" +
            //         "Você já tem um aluguel em andamento. \n" +
            //         "Seguem os dados do seu aluguel: \n\n" +
            //         "Numero de bicicleta: " + bicicleta.getNumero() + "\n" +
            //         "Marca de bicicleta: " + bicicleta.getMarca() + "\n" +
            //         "Modelo de bicicleta: " + bicicleta.getModelo() + "\n" +
            //         "hora de inicio: " + horaInicio + "\n" +
            //         "tranca de retirada: " + trancaInicio.getLocalizacao() + "\n\n" +
            //         "Se você não está usando a bicicleta, por favor, entre em contato com a gente.\n\n" +
            //         "Atenciosamente,\n" +
            //         "Equipe Aluguel de Bicicletas");
            
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
        throw new IllegalArgumentException(CICLISTA_EXCEPTION_1 + idCiclista + " não está em aluguel");
    }

    public Ciclista alteraCiclista(int idCiclista, CiclistaDTO dadosAlteracaoCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        validaNacionalidade(ciclista.getCpf(), ciclista.getPassaporte().getNumero(), dadosAlteracaoCiclista.nacionalidade());

        // TODO: enviar email para o ciclista informando que os dados foram alterados
            ///enviarEmail(ciclista.getEmail(), "Dados alterados no Aluguel de Bicicletas",
            //        "Olá " + ciclista.getNome() + ",\n\n" +
            //        "Seus dados foram alterados com sucesso!\n\n" +
            //        "Atenciosamente,\n" +
            //        "Equipe Aluguel de Bicicletas");

        ciclista.atualizaCiclista(dadosAlteracaoCiclista);
        return ciclista;
    }

    public BicicletaDTO recuperaBicicletaAlugada (int idCiclista) {
        /*Ciclista ciclista = */recuperaCiclistaPorIdEmAluguel(idCiclista);

        // TODO: return get /bicicleta/{idBicicleta}
        return new BicicletaDTO(3, "xupetada", "molenga", "25696969", 3, "Em uso");
    }

    private void validaNacionalidade(String cpf, String passaporte, String nacionalidade) {
        if (cpf.isEmpty() && nacionalidade.equals("brasileiro")) {
            throw new IllegalArgumentException("O CPF é obrigatório para brasileiros");
        } 
        if (passaporte.isEmpty() && nacionalidade.equals("estrangeiro")) {
            throw new IllegalArgumentException("O passaporte é obrigatório para estrangeiros");
        }
    }
}

