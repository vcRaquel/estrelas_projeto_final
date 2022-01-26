package br.com.zup.projeto_final.Livro;

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

}
