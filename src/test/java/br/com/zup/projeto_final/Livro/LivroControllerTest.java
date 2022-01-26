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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        livro.setNome("Livro");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setCurtidas(1);
        livro.setTags(Tags.LEITURA_LEVE);

        livroDTO = new LivroDTO();
        livroDTO.setNome("Livro");
        livroDTO.setGenero(Genero.AVENTURA);
        livroDTO.setLido(true);
        livroDTO.setCurtidas(1);
        livroDTO.setTags(Tags.LEITURA_LEVE);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeLivro()throws Exception{
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class))).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));

        String jsonDeResposta = resultado.andReturn().getResponse().getContentAsString();
        LivroDTO livroDTO = objectMapper.readValue(jsonDeResposta, LivroDTO.class);
    }

}
