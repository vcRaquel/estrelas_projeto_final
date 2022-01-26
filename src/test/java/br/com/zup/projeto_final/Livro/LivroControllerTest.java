package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.dto.UsuarioDTO;
import br.com.zup.projeto_final.Usuario.dto.UsuarioSaidaDTO;
import br.com.zup.projeto_final.seguranca.UsuarioLoginService;
import br.com.zup.projeto_final.seguranca.jwt.JWTComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({LivroController.class, ConversorModelMapper.class, UsuarioLoginService.class, JWTComponent.class})
public class LivroControllerTest {
    @MockBean
    LivroService livroService;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @MockBean
    private JWTComponent jwtComponent;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Livro livro;
    private LivroDTO livroDTO;
    private UsuarioSaidaDTO usuarioSaidaDTO;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        livro = new Livro();
        livro.setNome("");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setCurtidas(1);
        livro.setTags(Tags.LEITURA_LEVE);

        livroDTO.setNome("");
        livroDTO.setGenero(Genero.AVENTURA);
        livroDTO.setLido(true);
        livroDTO.setCurtidas(1);
        livroDTO.setTags(Tags.LEITURA_LEVE);

    }

}
