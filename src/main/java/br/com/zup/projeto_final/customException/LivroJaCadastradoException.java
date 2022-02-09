package br.com.zup.projeto_final.customException;

public class LivroJaCadastradoException extends RuntimeException{
    public LivroJaCadastradoException(String message) {
        super(message);
    }
}
