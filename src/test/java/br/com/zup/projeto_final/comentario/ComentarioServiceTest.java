package br.com.zup.projeto_final.comentario;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.model.Livro;
import br.com.zup.projeto_final.service.LivroService;
import br.com.zup.projeto_final.model.Comentario;
import br.com.zup.projeto_final.repository.ComentarioRepository;
import br.com.zup.projeto_final.service.ComentariosService;
import br.com.zup.projeto_final.customException.AtualizacaoInvalidaException;
import br.com.zup.projeto_final.customException.ComentarioNaoEncontradoException;
import br.com.zup.projeto_final.customException.DelecaoInvalidaException;
import br.com.zup.projeto_final.model.Usuario;
import br.com.zup.projeto_final.repository.UsuarioRepository;
import br.com.zup.projeto_final.service.UsuarioService;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import br.com.zup.projeto_final.service.UsuarioLogadoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    UsuarioService usuarioService;
    @MockBean
    UsuarioLogadoService usuarioLogadoService;

    @Autowired
    ComentariosService comentariosService;

    private Comentario comentario;
    private Usuario usuario;
    private Usuario usuario2;
    private Livro livro;
    private List<Comentario> comentarios;

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

        comentarios = new ArrayList<>();
        comentarios.add(comentario);

        livro = new Livro();
        livro.setNome("Livro");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setTags(Tags.LEITURA_LEVE);
        livro.setAutor("Autor");
        livro.setComentarios(comentarios);

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
        Mockito.when(usuarioService.buscarUsuario(Mockito.anyString())).thenThrow(new UsuarioNaoEncontradoException(""));

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

    @Test
    public void testarBuscarComentarioNaoEncontrado(){
        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ComentarioNaoEncontradoException exception = Assertions.assertThrows(ComentarioNaoEncontradoException.class, () ->{
            comentariosService.buscarComentario(0);
        });
        Mockito.verify(comentarioRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Assertions.assertEquals("Comentario não encontrado", exception.getMessage());
    }

    @Test
    public void testarAtualizarComentarios(){

        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comentario));
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn(usuario.getId());
        Mockito.when(comentarioRepository.save(Mockito.any(Comentario.class))).thenReturn(comentario);

        comentariosService.atualizarComentario(comentario.getId(), comentario);
        Mockito.verify(comentarioRepository, Mockito.times(1)).save(Mockito.any(Comentario.class));
    }

    @Test
    public void testarAtualizarComentarioNaoEncontrado(){

        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        ComentarioNaoEncontradoException exception = Assertions.assertThrows(ComentarioNaoEncontradoException.class, () ->{
            comentariosService.atualizarComentario(Mockito.anyInt(), comentario);
        });
        Mockito.verify(comentarioRepository, Mockito.times(1)).findById(Mockito.anyInt());
        Assertions.assertEquals("Comentario não cadastrado.", exception.getMessage());

    }

    @Test
    public void testarAtualizarComentarioDeOutraPessoa(){

        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comentario));
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn(usuario2.getId());

        AtualizacaoInvalidaException exception = Assertions.assertThrows(AtualizacaoInvalidaException.class, () ->{
            comentariosService.atualizarComentario(Mockito.anyInt(), comentario);
        });
    }

    @Test
    public void testarDeletarComentario(){
        Mockito.when(usuarioService.buscarUsuario(Mockito.anyString())).thenReturn(usuario);
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn(usuario.getId());
        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comentario));
        Mockito.when(livroService.buscarLivro(Mockito.anyInt())).thenReturn(livro);
        comentariosService.deletarComentario(comentario.getId());

        Mockito.verify(comentarioRepository, Mockito.times(1)).delete(comentario);

    }

    @Test
    public void testarDeletarComentarioNaoEncontrado(){

        Mockito.when(usuarioService.buscarUsuario(Mockito.anyString())).thenReturn(usuario);
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn(usuario.getId());
        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(livroService.buscarLivro(Mockito.anyInt())).thenReturn(livro);
        Assertions.assertThrows(ComentarioNaoEncontradoException.class, () ->{
            comentariosService.deletarComentario(comentario.getId());
        });

        Mockito.verify(comentarioRepository, Mockito.times(0)).delete(comentario);
    }

    @Test
    public void testarDeletarComentarioDeOutroUsuario(){

        Mockito.when(usuarioService.buscarUsuario(Mockito.anyString())).thenReturn(usuario2);
        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comentario));
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn(usuario2.getId());
        Mockito.when(livroService.buscarLivro(Mockito.anyInt())).thenReturn(livro);
        Assertions.assertThrows(DelecaoInvalidaException.class, () ->{
            comentariosService.deletarComentario(comentario.getId());
        });

        Mockito.verify(comentarioRepository, Mockito.times(0)).delete(comentario);
    }


}
