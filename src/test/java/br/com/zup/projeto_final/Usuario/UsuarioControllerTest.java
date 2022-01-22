package br.com.zup.projeto_final.Usuario;

import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.Usuario.dto.UsuarioDTO;
import br.com.zup.projeto_final.Usuario.dto.UsuarioSaidaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


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


}
