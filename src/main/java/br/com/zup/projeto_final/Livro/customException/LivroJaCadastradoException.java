package br.com.zup.projeto_final.Livro.customException;

public class LivroJaCadastradoException extends RuntimeException{
    public LivroJaCadastradoException(String message) {
        super(message);
    }
}
