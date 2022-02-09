package br.com.zup.projeto_final.customException;

public class AtualizacaoInvalidaException extends RuntimeException{

    public AtualizacaoInvalidaException(String message) {
        super(message);
    }
}
