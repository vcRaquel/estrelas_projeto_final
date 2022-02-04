package br.com.zup.projeto_final.Config;

import br.com.zup.projeto_final.Textos.comentario.customExceptions.AtualizacaoInvalidaException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.ComentarioNaoEncontradoException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.DelecaoInvalidaException;
import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import br.com.zup.projeto_final.customException.RecursoInexistente;
import br.com.zup.projeto_final.customException.UsuarioJaCadastradoException;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(CurtidaRepetidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroDeValidacao tratarUsuarioJaCadastradoException(
            CurtidaRepetidaException exception) {
        return new ErroDeValidacao(exception.getMessage());

    }

    @ExceptionHandler(RecursoInexistente.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDeValidacao tratarUsuarioJaCadastradoException(
            RecursoInexistente exception) {
        return new ErroDeValidacao(exception.getMessage());

    }

    @ExceptionHandler(LivroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDeValidacao tratarLivroNaoEncontradoException(
            LivroNaoEncontradoException livroNaoEncontradoException) {
        return new ErroDeValidacao(livroNaoEncontradoException.getMessage());

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroDeValidacao naoLegivelException (){
        return new ErroDeValidacao("Informação do JSON ilegível");
    }

    @ExceptionHandler(ComentarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDeValidacao tratarComentarioNaoEncontradoException(
            ComentarioNaoEncontradoException exception) {
        return new ErroDeValidacao(exception.getMessage());

    }

    @ExceptionHandler(AtualizacaoInvalidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroDeValidacao tratarAtualizacaoInvalidaException(
            AtualizacaoInvalidaException exception) {
        return new ErroDeValidacao(exception.getMessage());

    }

    @ExceptionHandler(DelecaoInvalidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroDeValidacao tratarDelecaoInvalidaException(
            DelecaoInvalidaException exception) {
        return new ErroDeValidacao(exception.getMessage());

    }

}
