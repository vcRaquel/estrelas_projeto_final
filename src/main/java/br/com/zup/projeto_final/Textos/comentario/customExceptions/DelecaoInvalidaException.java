package br.com.zup.projeto_final.Textos.comentario.customExceptions;

public class DelecaoInvalidaException extends RuntimeException{

    public DelecaoInvalidaException(String message) {
        super(message);
    }
}
