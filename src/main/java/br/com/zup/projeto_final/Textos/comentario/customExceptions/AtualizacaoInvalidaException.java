package br.com.zup.projeto_final.Textos.comentario.customExceptions;

public class AtualizacaoInvalidaException extends RuntimeException{

    public AtualizacaoInvalidaException(String message) {
        super(message);
    }
}
