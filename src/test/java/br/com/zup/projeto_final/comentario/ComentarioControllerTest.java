package br.com.zup.projeto_final.comentario;

import br.com.zup.projeto_final.Components.ConversorModelMapper;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.comentario.ComentariosController;
import br.com.zup.projeto_final.Textos.comentario.ComentariosService;
import br.com.zup.projeto_final.Textos.comentario.dtos.ComentarioDTO;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioController;
import br.com.zup.projeto_final.seguranca.UsuarioLoginService;
import br.com.zup.projeto_final.seguranca.jwt.JWTComponent;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

@WebMvcTest({ComentariosController.class, ConversorModelMapper.class, UsuarioLoginService.class, JWTComponent.class})
public class ComentarioControllerTest {

    @MockBean
    private ComentariosService comentariosService;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @MockBean
    private JWTComponent jwtComponent;
    @MockBean
    private UsuarioController usuarioController;
    @MockBean
    private UsuarioLogadoService usuarioLogadoService;

    @Autowired
    private MockMvc mockMvc;


    private ObjectMapper objectMapper;
    private Comentario comentario;
    private ComentarioDTO comentarioDTO;
    private Usuario usuario;


    @BeforeEach
    public void setup(){

        usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("a@a");
        usuario.setNome("aaaa");
        usuario.setSenha("1234");


        comentario = new Comentario();
        comentario.setQuemComentou(usuario);
        comentario.setId(1);
        comentario.setLivro_id(1);
        comentario.setTexto("a");


        comentarioDTO = new ComentarioDTO();
        comentarioDTO.setIdLivro(1);
        comentarioDTO.setTexto("a");

        objectMapper = new ObjectMapper();
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeComentario() throws Exception {

        Mockito.doNothing().when(comentariosService)
                .salvarComentario(Mockito.anyString(), Mockito.any(Comentario.class));
        String json = objectMapper.writeValueAsString(comentarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/comentarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));

    }
}
