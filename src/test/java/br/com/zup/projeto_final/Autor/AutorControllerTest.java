package br.com.zup.projeto_final.Autor;

import br.com.zup.projeto_final.Autor.dto.AutorDTO;
import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.customException.AutorNaoEncontradoException;
import br.com.zup.projeto_final.seguranca.UsuarioLoginService;
import br.com.zup.projeto_final.seguranca.jwt.JWTComponent;
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

@WebMvcTest({AutorController.class, ConversorModelMapper.class, UsuarioLoginService.class, JWTComponent.class})
public class AutorControllerTest {
    @MockBean
    private AutorService autorService;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @MockBean
    private JWTComponent jwtComponent;


    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Autor autor;
    private AutorDTO autorDTO;

    @BeforeEach
    public void setup(){
        autor = new Autor();
        autor.setNome("Clarice Lispector");
        autor.setId(1);

        autorDTO = new AutorDTO();
        autorDTO.setNome("Clarice Lispector");


        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeAutor() throws Exception{
        Mockito.when(autorService.salvarAutor(Mockito.any(Autor.class))).thenReturn(autor);
        String json = objectMapper.writeValueAsString(autorDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/autores")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        AutorDTO autorResposta = objectMapper.readValue(jsonResposta, AutorDTO.class);
    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeAutorValidacaoNome() throws Exception{
        autorDTO.setNome("");
        Mockito.when((autorService.salvarAutor(Mockito.any(Autor.class)))).thenReturn(autor);
        String json = objectMapper.writeValueAsString(autorDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/autores")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibicaoDeAutores() throws Exception{
        Mockito.when(autorService.buscarAutores()).thenReturn(Arrays.asList(autor));

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/autores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        List<AutorDTO> autores = objectMapper.readValue(jsonResposta, new TypeReference<List<AutorDTO>>()
        {});
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarAtualizarAutor() throws Exception {
        Mockito.when(autorService.atualizarAutor(Mockito.anyInt(),Mockito.any(Autor.class))).thenReturn(autor);
        String json = objectMapper.writeValueAsString(autorDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/autores/1")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        AutorDTO autorResposta = objectMapper.readValue(jsonResposta, AutorDTO.class);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarDeletarAutor() throws Exception {
        autor.setId(1);
        Mockito.doNothing().when(autorService).deletarAutor(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/autores/"+ autor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));

        Mockito.verify(autorService, Mockito.times(1)).deletarAutor(Mockito.anyInt());
    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarDeletarAutorNaoEncontrado() throws Exception {
        Mockito.doThrow(AutorNaoEncontradoException.class).when(autorService).deletarAutor(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/autores/"+ autor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

}
