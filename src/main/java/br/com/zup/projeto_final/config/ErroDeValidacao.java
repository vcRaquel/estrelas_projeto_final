package br.com.zup.projeto_final.config;

import lombok.Data;

@Data
public class ErroDeValidacao {
    private String mensagem;

    public ErroDeValidacao( String mensagem) {
        this.mensagem = mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
