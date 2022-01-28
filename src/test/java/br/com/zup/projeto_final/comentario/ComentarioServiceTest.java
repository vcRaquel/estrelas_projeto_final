package br.com.zup.projeto_final.comentario;

import br.com.zup.projeto_final.Livro.LivroService;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.comentario.ComentarioRepository;
import br.com.zup.projeto_final.Textos.comentario.ComentariosService;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioRepository;
import br.com.zup.projeto_final.Usuario.UsuarioService;
import br.com.zup.projeto_final.Usuario.customException.UsuarioJaCadastradoException;
import br.com.zup.projeto_final.Usuario.customException.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class ComentarioServiceTest {

    @MockBean
    ComentarioRepository comentarioRepository;
    @MockBean
    LivroService livroService;
    @MockBean
    UsuarioRepository usuarioRepository;

    @Autowired
    ComentariosService comentariosService;

    private Comentario comentario;
    private Usuario usuario;

    @BeforeEach
    public void setup(){
        usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("a@a");
        usuario.setNome("aaaa");
        usuario.setSenha("1234");


        comentario = new Comentario();
        comentario.setQuemComentou(usuario);
        comentario.setId(1);
        comentario.setIdLivro(1);
        comentario.setTexto("a");

    }

    @Test
    public void testarSalvarComentario(){
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.doNothing().when(livroService).atualizarComentariosDoLivro(Mockito.anyInt(), Mockito.any(Comentario.class));
        Mockito.when(comentarioRepository.save(comentario)).thenReturn(comentario);

        comentariosService.salvarComentario(Mockito.anyString(), comentario);
    }

    @Test
    public void testarSalvarComentarioUsuarioNaoEncontrado(){
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty())
                .thenThrow(new UsuarioNaoEncontradoException(""));

        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () ->{
            comentariosService.salvarComentario(Mockito.anyString(), comentario);
        });
    }
}
