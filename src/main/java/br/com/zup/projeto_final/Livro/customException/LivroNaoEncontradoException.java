package br.com.zup.projeto_final.Livro.customException;

public class LivroNaoEncontradoException extends RuntimeException{
    public LivroNaoEncontradoException(String message) {
        super(message);
    }

}
