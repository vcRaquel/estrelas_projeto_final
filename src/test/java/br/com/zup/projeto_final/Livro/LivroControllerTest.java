package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;
import br.com.zup.projeto_final.Usuario.UsuarioController;
import br.com.zup.projeto_final.Usuario.dto.UsuarioSaidaDTO;
import br.com.zup.projeto_final.seguranca.UsuarioLoginService;
import br.com.zup.projeto_final.seguranca.jwt.JWTComponent;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.Arrays;
import java.util.List;

@WebMvcTest({LivroController.class, ConversorModelMapper.class, UsuarioLoginService.class, JWTComponent.class})
public class LivroControllerTest {
    @MockBean
    LivroService livroService;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @MockBean
    private JWTComponent jwtComponent;
    @MockBean
    UsuarioController usuarioController;
    @MockBean
    private UsuarioLogadoService usuarioLogadoService;

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
        livro.setAutor("autor");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setTags(Tags.LEITURA_LEVE);

        livroDTO = new LivroDTO();
        livroDTO.setNome("Livro");
        livroDTO.setAutor("autor");
        livroDTO.setGenero(Genero.AVENTURA);
        livroDTO.setLido(true);
        livroDTO.setTags(Tags.LEITURA_LEVE);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeLivro() throws Exception {
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class), Mockito.anyString())).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));

        String jsonDeResposta = resultado.andReturn().getResponse().getContentAsString();
        LivroDTO livroDTO = objectMapper.readValue(jsonDeResposta, LivroDTO.class);
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeLivroValidacaoNome() throws Exception {
        livro.setNome("");
        livroDTO.setNome("");
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class), Mockito.anyString())).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeLivroValidacaoAutor() throws Exception {
        livro.setAutor("");
        livroDTO.setAutor("");
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class), Mockito.anyString())).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeLivroValidacaoGeneroNull() throws Exception {
        livro.setGenero(null);
        livroDTO.setGenero(null);
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class), Mockito.anyString())).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeLivroValidacaoGeneroInvalido() throws Exception {
        Mockito.when(livroService.salvarLivro(Mockito.any(Livro.class), Mockito.anyString())).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);
        json = json.replace("AVENTURA","TESTE");;

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibirLivros() throws Exception {
        Mockito.when(livroService.buscarLivros()).thenReturn(Arrays.asList(livro));

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/livros")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonDeResposta = resultado.andReturn().getResponse().getContentAsString();
        List<LivroDTO> livros = objectMapper.readValue(jsonDeResposta, new TypeReference<List<LivroDTO>>() {
        });

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibicaoDeLivro() throws Exception {

        Mockito.when(livroService.buscarLivro(Mockito.anyInt())).thenReturn(livro);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/livros/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        LivroDTO livroDTO = objectMapper.readValue(jsonResposta,
                LivroDTO.class);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibirLivroNaoEncontrado() throws Exception{
        Mockito.doThrow(LivroNaoEncontradoException.class).when(livroService).buscarLivro(Mockito.anyInt());

        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/livros/0")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarAtualizarLivro() throws Exception {
        Mockito.when(livroService.atualizarLivro(Mockito.anyInt(), Mockito.any(Livro.class))).thenReturn(livro);
        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/livros/1")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        LivroDTO livroResposta = objectMapper.readValue(jsonResposta, LivroDTO.class);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarAtualizarLivroNaoEncontrado() throws Exception{
        Mockito.doThrow(LivroNaoEncontradoException.class).when(livroService)
                .atualizarLivro(Mockito.anyInt(),Mockito.any(Livro.class));

        String json = objectMapper.writeValueAsString(livroDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/livros/0").content(json)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(404));
    }

    //testar atualizar livro sem ser o usuario que cadastrou

    @Test
    @WithMockUser("user@user.com")
    public void testarDeletarLivro() throws Exception{
        Mockito.doNothing().when(livroService).deletarLivro(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/livros/" + livro.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));

        Mockito.verify(livroService, Mockito.times(1)).deletarLivro(Mockito.anyInt());

    }

    //testar deletar livro sem ser o usuario que cadastrou

    @Test
    @WithMockUser("user@user.com")
    public void testarDeletarLivroNaoEncontrado() throws Exception {
        Mockito.doThrow(LivroNaoEncontradoException.class).when(livroService).deletarLivro(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/livros/" + livro.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

}
