package br.com.zup.projeto_final.Config;
import br.com.zup.projeto_final.Usuario.customException.UsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroDeValidacao tratarClienteNaoEncontradoException(
            UsuarioNaoEncontradoException clienteNaoEncontradoException) {
        return new ErroDeValidacao(clienteNaoEncontradoException.getMessage());
    }

}
