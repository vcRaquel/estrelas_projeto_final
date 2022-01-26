package br.com.zup.projeto_final.Config;
import br.com.zup.projeto_final.Usuario.customException.UsuarioJaCadastradoException;
import br.com.zup.projeto_final.Usuario.customException.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDeValidacao tratarUsuarioNaoEncontradoException(
            UsuarioNaoEncontradoException usuarioNaoEncontradoException) {
        return new ErroDeValidacao(usuarioNaoEncontradoException.getMessage());

    }

    @ExceptionHandler(UsuarioJaCadastradoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroDeValidacao tratarUsuarioJaCadastradoException(
            UsuarioJaCadastradoException usuarioJaCadastradoException) {
        return new ErroDeValidacao(usuarioJaCadastradoException.getMessage());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ErroDeValidacao> tratarErrosDeValidacao(MethodArgumentNotValidException excecao) {
        List<ErroDeValidacao> erros = new ArrayList<>();

        for (FieldError referencia : excecao.getFieldErrors()) {
            ErroDeValidacao mensagem = new ErroDeValidacao(referencia.getDefaultMessage());
            erros.add(mensagem);
        }
        return erros;

    }

}
