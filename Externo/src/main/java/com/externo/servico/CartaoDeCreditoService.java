package com.externo.servico;

import com.externo.DTO.CartaoDeCreditoDTO;
import com.externo.model.CartaoDeCredito;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.externo.model.CartaoDeCredito.cartoesDeCredito;

@Service
public class CartaoDeCreditoService {

    //valida o cartao de credito, retornando true se for valido e false se for invalido
    public boolean validaCartaoDeCredito(CartaoDeCreditoDTO dadosCadastroCartao){
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
