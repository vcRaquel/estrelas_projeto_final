package br.com.zup.projeto_final.Usuario;

import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.Usuario.dto.UsuarioDTO;
import br.com.zup.projeto_final.Usuario.dto.UsuarioSaidaDTO;
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


@WebMvcTest({UsuarioController.class, ConversorModelMapper.class})
public class UsuarioControllerTest {
    @MockBean
    UsuarioService usuarioService;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;
    private UsuarioSaidaDTO usuarioSaidaDTO;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail("usuario@email.com");
        usuario.setNome("Zupper");
        usuario.setSenha("senha123");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("usuario@email.com");
        usuarioDTO.setNome("Zupper");

        usuarioSaidaDTO = new UsuarioSaidaDTO();
        usuarioSaidaDTO.setNome("Zupper");
        usuarioSaidaDTO.setEmail("usuario@email.com");


        objectMapper = new ObjectMapper();
    }

    @Test
    public void testarCadastroDeUsuario() throws Exception {
        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        UsuarioSaidaDTO usuarioResposta = objectMapper.readValue(jsonResposta, UsuarioSaidaDTO.class);
    }

    @Test //TDD
    public void testarCadastroDeUsuarioValidacaoNome() throws Exception {
        usuarioDTO.setNome("");
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    public void testarExibicaoDeUsuario() throws Exception {
        Mockito.when(usuarioService.buscarUsuarios()).thenReturn(Arrays.asList(usuario));

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        List<UsuarioSaidaDTO> usuarios = objectMapper.readValue(jsonResposta,
                new TypeReference<List<UsuarioSaidaDTO>>(){});

    }


}
