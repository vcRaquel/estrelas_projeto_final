package br.com.zup.projeto_final.customException;

public class ComentarioNaoEncontradoException extends RuntimeException{

    public ComentarioNaoEncontradoException(String message) {
        super(message);
    }
}
