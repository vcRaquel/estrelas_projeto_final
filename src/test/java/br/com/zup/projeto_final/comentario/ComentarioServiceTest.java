package br.com.zup.projeto_final.comentario;

import br.com.zup.projeto_final.Livro.LivroService;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.comentario.ComentarioRepository;
import br.com.zup.projeto_final.Textos.comentario.ComentariosService;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.AtualizacaoInvalidaException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.ComentarioNaoEncontradoException;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioRepository;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class ComentarioServiceTest {

    @MockBean
    ComentarioRepository comentarioRepository;
    @MockBean
    LivroService livroService;
    @MockBean
    UsuarioRepository usuarioRepository;
    @MockBean
    UsuarioLogadoService usuarioLogadoService;

    @Autowired
    ComentariosService comentariosService;

    private Comentario comentario;
    private Usuario usuario;
    private Usuario usuario2;

    @BeforeEach
    public void setup(){
        usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("a@a");
        usuario.setNome("aaaa");
        usuario.setSenha("1234");

        usuario2 = new Usuario();
        usuario2.setId("2");
        usuario2.setEmail("@@@@aaa");
        usuario2.setNome("usuarioquenaocadastrou");
        usuario2.setSenha("1234");


        comentario = new Comentario();
        comentario.setQuemComentou(usuario);
        comentario.setId(1);
        comentario.setLivro_id(1);
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

    @Test
    public void testarBuscarComentarios(){
        Mockito.when(comentarioRepository.findAll()).thenReturn(Arrays.asList(comentario));
        comentariosService.buscarComentarios();
        Mockito.verify(comentarioRepository, Mockito.times(1)).findAll();

    }


    @Test
    public void testarBuscarComentario(){
        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comentario));
        comentariosService.buscarComentario(Mockito.anyInt());
        Mockito.verify(comentarioRepository, Mockito.times(1)).findById(Mockito.anyInt());

    }

}
