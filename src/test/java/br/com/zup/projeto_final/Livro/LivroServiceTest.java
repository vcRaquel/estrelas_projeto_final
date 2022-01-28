package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;
import br.com.zup.projeto_final.Textos.Review;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioRepository;
import br.com.zup.projeto_final.Usuario.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class LivroServiceTest {
    @MockBean
    LivroRepository livroRepository;
    @MockBean
    UsuarioService usuarioService;

    @Autowired
    LivroService livroService;

    private Livro livro;
    private Usuario usuario;
    private Comentario comentario;
    private List<Comentario>comentarios;
    private Review review;

    @BeforeEach
    public void setup() {
        review = new Review();
        review.setId(1);
        review.setTexto("Este é um exemplo de review");

        comentarios = new ArrayList<>();

        livro = new Livro();
        livro.setNome("Livro");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setTags(Tags.LEITURA_LEVE);
        livro.setAutor("Autor");
        livro.setReview(review);
        livro.setComentarios(comentarios);

        usuario = new Usuario();
        usuario.setId("1");
        usuario.setSenha("aviao11");
        usuario.setEmail("zupper@zup.com");
        usuario.setNome("Zupper");
        usuario.setLivrosCadastrados(Arrays.asList(livro));

        comentario = new Comentario();
        comentario.setQuemComentou(usuario);
        comentario.setId(1);
        comentario.setIdLivro(1);
        comentario.setTexto("Algum comentário");

    }

    @Test
    public void testarSalvarLivro() {
        Mockito.when(livroRepository.save(livro))
                .thenAnswer(objto -> objto.getArgument(0, Livro.class));
        Mockito.doNothing().when(usuarioService).atualizarLivrosDoUsuario(usuario.getId(), livro);

        livroService.salvarLivro(livro, usuario.getId());

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);

    }


    //testar salvar livro repetido

    @Test
    public void testarBuscarLivros() {
        List<Livro> livros = Arrays.asList(livro);
        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<Livro> livrosResposta = livroService.buscarLivros();
        Assertions.assertNotNull(livrosResposta);
        Mockito.verify(livroRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarBuscarLivro() {
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));
        Livro livroResposta = livroService.buscarLivro(Mockito.anyInt());

        Assertions.assertNotNull(livroResposta);
        Assertions.assertEquals(Livro.class, livroResposta.getClass());
        Assertions.assertEquals(livro.getId(), livroResposta.getId());
    }

    //testar buscar livro que não existe
    @Test
    public void testarBuscarLivroNaoEncontrado(){
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        LivroNaoEncontradoException exception = Assertions.assertThrows(LivroNaoEncontradoException.class, () ->{
            livroService.buscarLivro(0);
        });

        Assertions.assertEquals("Livro não encontrado", exception.getMessage());

    }

    //testar atualizar comentários do livro
    @Test
    public void testarAtualizarComentariosDoLivro(){
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);

        livroService.atualizarComentariosDoLivro(Mockito.anyInt(), comentario);

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);

    }

    @Test
    public void testarAtualizarLivro() {
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);

        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));

        livroService.atualizarLivro(Mockito.anyInt(), livro);

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);
    }

    //testar atualizar livro que não existe


}
