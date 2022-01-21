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


}
