package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.Enun.Tipo;
import br.com.zup.projeto_final.curtida.dtos.CurtidaEntradaDTO;
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

@WebMvcTest({CurtidaController.class, ConversorModelMapper.class, UsuarioLoginService.class, JWTComponent.class})

public class CurtidaControllerTest {

    @MockBean
    CurtidaService curtidaService;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @MockBean
    private JWTComponent jwtComponent;


    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Curtida curtida;
    private CurtidaEntradaDTO curtidaEntradaDTO;

    @BeforeEach
    public void setup(){
        objectMapper = new ObjectMapper();

        curtida = new Curtida();
        curtida.setId_curtida(1L);
        curtida.setId_recurso(1L);
        curtida.setId_usuario("1");
        curtida.setTipo(Tipo.COMENTARIO);

        curtidaEntradaDTO = new CurtidaEntradaDTO();
        curtidaEntradaDTO.setId_recurso(1L);
        curtidaEntradaDTO.setTipo(Tipo.COMENTARIO);
        curtidaEntradaDTO.setId_usuario("1");
    }


    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeCurtida() throws Exception {
        Mockito.doNothing().when(curtidaService).salvarCurtida(curtida);

        String json = objectMapper.writeValueAsString(curtidaEntradaDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/curtida")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));
    }
}
