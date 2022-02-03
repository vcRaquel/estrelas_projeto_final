package br.com.zup.projeto_final.Textos.comentario.customExceptions;

public class ComentarioNaoEncontradoException extends RuntimeException{

    public ComentarioNaoEncontradoException(String message) {
        super(message);
    }
}
