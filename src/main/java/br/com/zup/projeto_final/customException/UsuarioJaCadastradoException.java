package br.com.zup.projeto_final.customException;

public class UsuarioJaCadastradoException extends RuntimeException{
    public UsuarioJaCadastradoException(String message) {
        super(message);
    }

}
