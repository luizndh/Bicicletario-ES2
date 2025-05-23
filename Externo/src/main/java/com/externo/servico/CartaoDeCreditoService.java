package com.externo.servico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.externo.dto.CartaoDeCreditoDTO;

@Service
public class CartaoDeCreditoService {

    //valida o cartao de credito, retornando true se for valido e false se for invalido
    public Map<String, String> validaCartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartao){
        if(dadosCadastroCartao.nomeTitular().isEmpty() || dadosCadastroCartao.nomeTitular() == null){
            throw new IllegalArgumentException("O nome " + dadosCadastroCartao.nomeTitular() + " esta vazio ou nulo");
        }
        if (dadosCadastroCartao.numero().length() != 16) {
            throw new IllegalArgumentException("O tamanho do numero " + dadosCadastroCartao.numero() + " nao eh 16");
        }
        if (dadosCadastroCartao.cvv().length() != 3) {
            throw new IllegalArgumentException("O tamanho do cvv " + dadosCadastroCartao.cvv() + " nao eh 3");
        }
        if(!dadosCadastroCartao.nomeTitular().matches("[a-zA-ZÀ-ÿ '-]+")){
            throw new IllegalArgumentException("O nome " + dadosCadastroCartao.nomeTitular() + " nao eh valido");
        }
        if(!validaData(dadosCadastroCartao.validade(), "MM/yyyy")){
            throw new IllegalArgumentException("Formato incorreto da data " + dadosCadastroCartao.validade());
        }
        Map<String, String> response = new HashMap<>();
        response.put("codigo", "200");
        response.put("mensagem", "Cartao Valido");

        return response;
    }
    private boolean validaData(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
