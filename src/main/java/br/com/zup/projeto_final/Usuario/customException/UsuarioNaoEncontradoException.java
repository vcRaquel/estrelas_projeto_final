package br.com.zup.projeto_final.Usuario.customException;

public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

}
