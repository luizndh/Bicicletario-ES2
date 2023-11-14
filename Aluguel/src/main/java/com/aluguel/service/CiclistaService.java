package com.aluguel.service;

import com.aluguel.DTO.BicicletaDTO;
import com.aluguel.DTO.CiclistaDTO;
import com.aluguel.model.Aluguel;
import com.aluguel.model.Ciclista;

import java.util.List;

import static com.aluguel.model.Aluguel.alugueis;
import static com.aluguel.model.Ciclista.ciclistas;

public class CiclistaService {
    public Ciclista recuperaCiclistaPorId(int idCiclista) {
        for (Ciclista ciclista : ciclistas) {
            if (ciclista.getId() == idCiclista) {
                return ciclista;
            }
        }
        throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não existe");
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
    public Ciclista cadastraCiclista(CiclistaDTO dadosCadastroCiclista) {
        if (dadosCadastroCiclista.cpf().isEmpty() && dadosCadastroCiclista.nacionalidade().equals("brasileiro")) {
            throw new IllegalArgumentException("O CPF é obrigatório para brasileiros");
        } else if (dadosCadastroCiclista.passaporte().getNumero().isEmpty() && dadosCadastroCiclista.nacionalidade() == "estrangeiro") {
            throw new IllegalArgumentException("O passaporte é obrigatório para estrangeiros");
        } else {
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
    }

    // Onde diabos ele quer que "O sistema registre a data/hora da confirmação."?
    public Ciclista ativaCiclista(int idCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        if (!ciclista.isAtivo()) {
            ciclista.ativaCiclista();
            return ciclista;
        }

        throw new IllegalArgumentException("O ciclista com id " + idCiclista + " já está ativo");
    }

    public boolean verificaSeCiclistaPodeAlugar(int idCiclista) {
        Ciclista ciclista = recuperaCiclistaPorId(idCiclista);

        if (!ciclista.isAtivo()) {
            throw new IllegalArgumentException("O ciclista com id " + idCiclista + " não está ativo");
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
            
            throw new RuntimeException("Ciclista já tem aluguel em andamento");
        }
        return true;
    }
    
    private Ciclista recuperaCiclistaPorIdEmAluguel(int idCiclista) {
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

        if (ciclista.getCpf().isEmpty() && ciclista.getNacionalidade().equals("brasileiro")) {
            throw new IllegalArgumentException("O CPF é obrigatório para brasileiros");
        } 
        if (ciclista.getPassaporte().getNumero().isEmpty() && ciclista.getNacionalidade().equals("estrangeiro")) {
            throw new IllegalArgumentException("O passaporte é obrigatório para estrangeiros");
        }

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
        recuperaCiclistaPorId(idCiclista);

        if (!alugueis.isEmpty()) {
            for (Aluguel aluguel : alugueis) {
                if (aluguel.getCiclista() == idCiclista) {
                    // TODO: return get /bicicleta/{idBicicleta}
                    return new BicicletaDTO(3, "xupetada", "molenga", "25696969", 3, "Em uso");
                }
            }
        }
        return new BicicletaDTO(-1, "", "", "", -1, "");
    }

}

