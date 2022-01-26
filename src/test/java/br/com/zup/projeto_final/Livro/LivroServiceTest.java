package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

}
