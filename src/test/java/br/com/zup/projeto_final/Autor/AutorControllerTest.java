package br.com.zup.projeto_final.Autor;

import br.com.zup.projeto_final.Autor.customException.AutorNaoEncontradoException;
import br.com.zup.projeto_final.Autor.dto.AutorDTO;
import br.com.zup.projeto_final.Components.ConversorModelMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest({AutorController.class, ConversorModelMapper.class})
public class AutorControllerTest {
    @MockBean
    private AutorService autorService;

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
    public void testarCadastroDeAutor() throws Exception{
        Mockito.when(autorService.salvarAutor(Mockito.any(Autor.class))).thenReturn(autor);
        String json = objectMapper.writeValueAsString(autorDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/autores")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        AutorDTO autorResposta = objectMapper.readValue(jsonResposta, AutorDTO.class);
    }

    @Test
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



}
