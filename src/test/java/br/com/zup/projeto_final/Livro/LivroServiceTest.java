package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class LivroServiceTest {
    @MockBean
    LivroRepository livroRepository;

    @Autowired
    LivroService livroService;

    private Livro livro;

    @BeforeEach
    public void setup(){
        livro = new Livro();
        livro.setNome("Livro");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setCurtidas(1);
        livro.setTags(Tags.LEITURA_LEVE);

    }

    @Test
    public void testarSalvarLivro(){
        Mockito.when(livroRepository.save(livro)).thenAnswer(objto -> objto.getArgument(0,Livro.class));

        livroService.salvarLivro(livro);

        Mockito.verify(livroRepository, Mockito.times(1)).save(livro);

    }
    //testar salvar livro repetido

    @Test
    public void testarBuscarLivros(){
        List<Livro> livros = Arrays.asList(livro);
        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<Livro> livrosResposta = livroService.buscarLivros();
        Assertions.assertNotNull(livrosResposta);
        Mockito.verify(livroRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testarBuscarLivro(){
        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));
        Livro livroResposta = livroService.buscarLivro(Mockito.anyInt());

        Assertions.assertNotNull(livroResposta);
        Assertions.assertEquals(Livro.class, livroResposta.getClass());
        Assertions.assertEquals(livro.getId(), livroResposta.getId());
    }

    //testar buscar livro que não existe

    @Test
    public void testarAtualizarLivro(){
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);

        Mockito.when(livroRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(livro));

        livroService.atualizarLivro(Mockito.anyInt(), livro);
    }

}
