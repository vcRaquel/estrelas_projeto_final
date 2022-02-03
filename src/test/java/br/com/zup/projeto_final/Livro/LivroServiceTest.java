package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;
import br.com.zup.projeto_final.Textos.Review;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.DelecaoInvalidaException;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioService;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import io.jsonwebtoken.lang.Assert;
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
    @MockBean
    UsuarioLogadoService usuarioLogadoService;

    @Autowired
    LivroService livroService;

    private Livro livro;
    private Livro livro2;
    private List <Livro> livros;
    private Usuario usuario;
    private Usuario usuario2;
    private Comentario comentario;
    private Comentario comentario2;
    private List<Comentario> umComentario;
    private List<Comentario> doisComentarios;
    private Review review;


    @BeforeEach
    public void setup() {
        review = new Review();
        review.setId(1);
        review.setTexto("Este é um exemplo de review");

        umComentario = new ArrayList<>();
        doisComentarios = new ArrayList<>();

        comentario = new Comentario();
        comentario.setQuemComentou(usuario);
        comentario.setId(1);
        comentario.setLivro_id(1);
        comentario.setTexto("Algum comentário");
        umComentario.add(comentario);
        doisComentarios.add(comentario);

        livro = new Livro();
        livro.setNome("Livro");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setTags(Tags.LEITURA_LEVE);
        livro.setAutor("Autor");
        livro.setReview(review);
        livro.setComentarios(umComentario);

        comentario2 = new Comentario();
        comentario2.setQuemComentou(usuario);
        comentario2.setId(1);
        comentario2.setLivro_id(1);
        comentario2.setTexto("Algum comentário");
        doisComentarios.add(comentario2);


        livro2 = new Livro();
        livro2.setNome("Livro");
        livro2.setGenero(Genero.AVENTURA);
        livro2.setId(1);
        livro2.setLido(true);
        livro2.setTags(Tags.LEITURA_LEVE);
        livro2.setAutor("Autor");
        livro2.setReview(review);
        livro2.setComentarios(doisComentarios);

        livros = new ArrayList<>();
        livros.add(livro);
        livros.add(livro2);

        usuario = new Usuario();
        usuario.setId("1");
        usuario.setSenha("aviao11");
        usuario.setEmail("zupper@zup.com");
        usuario.setNome("Zupper");
        usuario.setLivrosCadastrados(Arrays.asList(livro));

        usuario2 = new Usuario();
        usuario2.setId("2");
        usuario2.setSenha("aviao12");
        usuario2.setEmail("usuario2@@@@");
        usuario2.setNome("usuario2");



    }

    @Test
    public void testarSalvarLivro() {
        Mockito.when(livroRepository.save(livro))
                .thenAnswer(objto -> objto.getArgument(0, Livro.class));
        Mockito.doNothing().when(usuarioService).atualizarLivrosDoUsuario(usuario.getId(), livro);

        livroService.salvarLivro(livro, usuario.getId());

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);

    }

    //testar salvar livro repetido (implementar regra)

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

    @Test
    public void testarBuscarLivroNaoEncontrado(){
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        LivroNaoEncontradoException exception = Assertions.assertThrows(LivroNaoEncontradoException.class, () ->{
            livroService.buscarLivro(0);
        });

        Assertions.assertEquals("Livro não encontrado", exception.getMessage());

    }

    @Test
    public void testarAtualizarComentariosDoLivro(){
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);

        livroService.atualizarComentariosDoLivro(Mockito.anyInt(), comentario);

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);

    }

    @Test
    public void testarAtualizarComentariosDoLivroNaoEncontrado(){
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);

        LivroNaoEncontradoException exception = Assertions.assertThrows(LivroNaoEncontradoException.class, () ->{
            livroService.atualizarComentariosDoLivro(Mockito.anyInt(), comentario);
        });

        Assertions.assertEquals("Livro não encontrado", exception.getMessage());

    }

    @Test
    public void testarAtualizarLivro() {
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);

        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));

        livroService.atualizarLivro(Mockito.anyInt(), livro);

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);

    }

    @Test
    public void testarAtualizarLivroNaoEncontrado(){
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        LivroNaoEncontradoException exception = Assertions.assertThrows(LivroNaoEncontradoException.class,
                () -> livroService.atualizarLivro(0,livro));

        Assertions.assertEquals("Livro não cadastrado.", exception.getMessage());

    }

    @Test
    public void testarDeletarLivro(){
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));
        Mockito.doNothing().when(livroRepository).deleteById(Mockito.anyInt());

        livroService.deletarLivro(Mockito.anyInt());

        Mockito.verify(livroRepository, Mockito.times(1)).deleteById(Mockito.anyInt());

    }

    @Test
    public void testarDeletarLivroNaoEncontrado(){
        Mockito.doNothing().when(livroRepository).deleteById(Mockito.anyInt());

        LivroNaoEncontradoException exception = Assertions.assertThrows(LivroNaoEncontradoException.class, () ->{
            livroService.deletarLivro(0);
        });

    }

    @Test
    public void testarDeletarLivroDeOutroUsuario(){
        Mockito.when(usuarioService.buscarUsuario(Mockito.anyString())).thenReturn(usuario2);
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn(usuario2.getId());
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));
        Assertions.assertThrows(DelecaoInvalidaException.class, () ->{
            livroService.deletarLivro(comentario.getId());
        });


    }

    @Test
    public void testarOrdenarLista(){
        List<Livro> livrosOrdenados = livroService.ordenarLista(livros);
        Assertions.assertEquals(livrosOrdenados.get(0), livro2);
    }

    @Test
    public void testarAplicarFiltrosFindAll(){
        livro.setGenero(null);
        livro.setAutor("");
        livro.setTags(null);
        livro.setNome("");

        livroService.aplicarFiltros(livro.getGenero(), livro.getTags(), livro.getNome(), livro.getAutor());

        Mockito.verify(livroRepository, Mockito.times(1)).findAll();
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroGenero(
                String.valueOf(Genero.AVENTURA), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroTags(
                String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroNomeEAutor(
                livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarTodosFiltros(
                String.valueOf(Genero.AVENTURA), String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());

    }

    @Test
    public void testarAplicarFiltrosNomeEAutor(){
        livro.setGenero(null);
        livro.setTags(null);

        livroService.aplicarFiltros(livro.getGenero(), livro.getTags(), livro.getNome(), livro.getAutor());

        Mockito.verify(livroRepository, Mockito.times(0)).findAll();
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroGenero(
                String.valueOf(Genero.AVENTURA), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroTags(
                String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(1)).aplicarFiltroNomeEAutor(
                livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarTodosFiltros(
                String.valueOf(Genero.AVENTURA), String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());

    }

    @Test
    public void testarAplicarFiltroGenero(){
        livro.setAutor("");
        livro.setTags(null);
        livro.setNome("");

        livroService.aplicarFiltros(livro.getGenero(), livro.getTags(), livro.getNome(), livro.getAutor());

        Mockito.verify(livroRepository, Mockito.times(0)).findAll();
        Mockito.verify(livroRepository, Mockito.times(1)).aplicarFiltroGenero(
                String.valueOf(Genero.AVENTURA), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroTags(
                String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroNomeEAutor(
                livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarTodosFiltros(
                String.valueOf(Genero.AVENTURA), String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());

    }

    @Test
    public void testarAplicarFiltrosTags(){
        livro.setGenero(null);
        livro.setAutor("");
        livro.setNome("");

        livroService.aplicarFiltros(livro.getGenero(), livro.getTags(), livro.getNome(), livro.getAutor());

        Mockito.verify(livroRepository, Mockito.times(0)).findAll();
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroGenero(
                String.valueOf(Genero.AVENTURA), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(1)).aplicarFiltroTags(
                String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroNomeEAutor(
                livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarTodosFiltros(
                String.valueOf(Genero.AVENTURA), String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());

    }

    @Test
    public void testarAplicarTodosOsFiltros(){
        livro.setAutor("");
        livro.setNome("");

        livroService.aplicarFiltros(livro.getGenero(), livro.getTags(), livro.getNome(), livro.getAutor());

        Mockito.verify(livroRepository, Mockito.times(0)).findAll();
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroGenero(
                String.valueOf(Genero.AVENTURA), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroTags(
                String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(0)).aplicarFiltroNomeEAutor(
                livro.getNome(), livro.getAutor());
        Mockito.verify(livroRepository, Mockito.times(1)).aplicarTodosFiltros(
                String.valueOf(Genero.AVENTURA), String.valueOf(Tags.LEITURA_LEVE), livro.getNome(), livro.getAutor());

    }

    @Test
    public void testarExibirTodosOsLivros(){
        livro.setNome("");
        livro.setAutor("");
        livroService.exibirTodosOsLivros(Genero.TECNICO, Tags.LEITURA_LEVE, livro.getNome(), livro.getAutor());
        Assertions.assertNotNull(livro.getNome(), livro.getAutor());

    }

}
