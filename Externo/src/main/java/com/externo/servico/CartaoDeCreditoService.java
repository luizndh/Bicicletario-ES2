package com.externo.servico;

import com.externo.DTO.CartaoDeCreditoDTO;
import com.externo.model.CartaoDeCredito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.externo.model.CartaoDeCredito.cartoesDeCredito;

public class CartaoDeCreditoService {
    /*
    public CartaoDeCredito recuperaCartaoDeCreditoPorId(int idCartao) {
        if(!cartoesDeCredito.isEmpty()) {
            for (CartaoDeCredito b : cartoesDeCredito) {
                if (b.getId() == idCartao) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("O cartão com id " + idCartao + " não existe");
    }

    public List<CartaoDeCredito> recuperaCartoesDeCredito() { return cartoesDeCredito; }

    public CartaoDeCredito cadastraCartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartao) {
        CartaoDeCredito b = new CartaoDeCredito(dadosCadastroCartao);
        cartoesDeCredito.add(b);
        return b;
    }

    public CartaoDeCredito alteraCartaoDeCredito(int idCartao, CartaoDeCreditoDTO dadosCadastroCartao) {
        if(!cartoesDeCredito.isEmpty()) {
            for (CartaoDeCredito b : cartoesDeCredito) {
                if (b.getId() == idCartao) {
                    b.atualizaCartaoDeCredito(dadosCadastroCartao);
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("O cartao com id " + idCartao + " não existe");
    }

    public void excluiCartaoDeCredito(int idCartao) {
        if(!cartoesDeCredito.isEmpty()) {
            for (CartaoDeCredito b : cartoesDeCredito) {
                if (b.getId() == idCartao) {
                    cartoesDeCredito.remove(b);
                    return;
                }
            }
        }
        throw new IllegalArgumentException("O cartao com id " + idCartao + " não existe");
    }
    */

    //true=valido, false=invalido
    public boolean validaCartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartao){
        //valida o cartao de credito, retornando true se for valido e false se for invalido
        if (dadosCadastroCartao.numero().length() != 16 || dadosCadastroCartao.cvv().length() != 3) {
            return false;
        }
        if(!validaData(dadosCadastroCartao.validade(), "MM/yy")){
            return false;
        } //TODO api?

        return true;
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
