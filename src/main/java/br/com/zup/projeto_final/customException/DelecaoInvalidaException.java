package br.com.zup.projeto_final.customException;

public class DelecaoInvalidaException extends RuntimeException{

    public DelecaoInvalidaException(String message) {
        super(message);
    }
}
