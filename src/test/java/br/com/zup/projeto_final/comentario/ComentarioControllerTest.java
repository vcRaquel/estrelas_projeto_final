package br.com.zup.projeto_final.comentario;

import br.com.zup.projeto_final.components.ConversorModelMapper;
import br.com.zup.projeto_final.model.Comentario;
import br.com.zup.projeto_final.controller.ComentariosController;
import br.com.zup.projeto_final.service.ComentariosService;
import br.com.zup.projeto_final.customException.ComentarioNaoEncontradoException;
import br.com.zup.projeto_final.dtos.ComentarioDTO;
import br.com.zup.projeto_final.model.Usuario;
import br.com.zup.projeto_final.controller.UsuarioController;
import br.com.zup.projeto_final.security.UsuarioLoginService;
import br.com.zup.projeto_final.security.jwt.JWTComponent;
import br.com.zup.projeto_final.service.UsuarioLogadoService;
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
        comentarioDTO.setLivro_id(1);
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

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeComentarioValidacaoNome() throws Exception {
        comentarioDTO.setTexto("");
        Mockito.doNothing().when(comentariosService).salvarComentario(Mockito.anyString(), Mockito.any(Comentario.class));
        String json = objectMapper.writeValueAsString(comentarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/comentarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeComentarioValidacaoIdLivro() throws Exception {
        comentarioDTO.setLivro_id(null);
        Mockito.doNothing().when(comentariosService).salvarComentario(Mockito.anyString(), Mockito.any(Comentario.class));
        String json = objectMapper.writeValueAsString(comentarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/comentarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibirComentarios() throws Exception {
        Mockito.when(comentariosService.buscarComentarios()).thenReturn(Arrays.asList(comentario));

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/comentarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonDeResposta = resultado.andReturn().getResponse().getContentAsString();
        List<ComentarioDTO> comentarioDTOS = objectMapper.readValue(jsonDeResposta,
                new TypeReference<List<ComentarioDTO>>() {});

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibirComentario() throws Exception {
        Mockito.when(comentariosService.buscarComentario(Mockito.anyInt())).thenReturn(comentario);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/comentarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));


        String jsonDeResposta = resultado.andReturn().getResponse().getContentAsString();
        ComentarioDTO comentarioDTO = objectMapper.readValue(jsonDeResposta,
                ComentarioDTO.class);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibirComentarioNaoEncontrado() throws Exception{
        Mockito.doThrow(ComentarioNaoEncontradoException.class).when(comentariosService).buscarComentario(Mockito.anyInt());

        String json = objectMapper.writeValueAsString(comentarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/livros/0")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    @WithMockUser("user@user.com")
    public void testarAtualizarComentario() throws Exception {
        Mockito.when(comentariosService.atualizarComentario(Mockito.anyInt(), Mockito.any(Comentario.class))).thenReturn(comentario);
        String json = objectMapper.writeValueAsString(comentarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/comentarios/1")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        ComentarioDTO comentarioResposta = objectMapper.readValue(jsonResposta, ComentarioDTO.class);

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarAtualizarComentarioNaoEncontrado() throws Exception {
        Mockito.doThrow(ComentarioNaoEncontradoException.class).when(comentariosService)
                .atualizarComentario(Mockito.anyInt(), Mockito.any(Comentario.class));
        String json = objectMapper.writeValueAsString(comentarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/comentarios/1")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarDeletarLivro() throws Exception{
        Mockito.doNothing().when(comentariosService).deletarComentario(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/comentarios/" + comentario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));

        Mockito.verify(comentariosService, Mockito.times(1)).deletarComentario(Mockito.anyInt());

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarDeletarLivroNaoEncontrado() throws Exception {
        Mockito.doThrow(ComentarioNaoEncontradoException.class).when(comentariosService)
                .deletarComentario(Mockito.anyInt());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/comentarios/" + comentario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }


}
