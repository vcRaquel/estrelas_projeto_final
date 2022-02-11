package br.com.zup.projeto_final.Usuario;

import br.com.zup.projeto_final.components.ConversorModelMapper;

import br.com.zup.projeto_final.dtos.SaidaCadastroDTO;
import br.com.zup.projeto_final.enuns.Operacao;
import br.com.zup.projeto_final.dtos.AtualizarInteressesDTO;
import br.com.zup.projeto_final.dtos.UsuarioDTO;
import br.com.zup.projeto_final.dtos.UsuarioSaidaDTO;
import br.com.zup.projeto_final.controller.UsuarioController;
import br.com.zup.projeto_final.customException.UsuarioJaCadastradoException;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import br.com.zup.projeto_final.model.Usuario;
import br.com.zup.projeto_final.security.UsuarioLoginService;
import br.com.zup.projeto_final.security.jwt.JWTComponent;
import br.com.zup.projeto_final.service.UsuarioService;
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

@WebMvcTest({UsuarioController.class, ConversorModelMapper.class, UsuarioLoginService.class, JWTComponent.class})

public class UsuarioControllerTest {
    @MockBean
    UsuarioService usuarioService;
    @MockBean
    private UsuarioLoginService usuarioLoginService;
    @MockBean
    private JWTComponent jwtComponent;
    @MockBean
    private UsuarioLogadoService usuarioLogadoService;

    @Autowired
    MockMvc mockMvc;

    private ObjectMapper objectMapper;
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;
    private UsuarioSaidaDTO usuarioSaidaDTO;
    private AtualizarInteressesDTO atualizarInteressesDTO;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("usuario@email.com");
        usuario.setNome("Zupper");
        usuario.setSenha("senha123");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail("usuario@email.com");
        usuarioDTO.setNome("Zupper");
        usuarioDTO.setSenha("aviao11");

        usuarioSaidaDTO = new UsuarioSaidaDTO();
        usuarioSaidaDTO.setNome("Zupper");
        usuarioSaidaDTO.setEmail("usuario@email.com");

        atualizarInteressesDTO = new AtualizarInteressesDTO();
        atualizarInteressesDTO.setId_livro(1);
        atualizarInteressesDTO.setOperacao(Operacao.INSERIR);

        objectMapper = new ObjectMapper();

    }


    @Test
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuario() throws Exception {

        Mockito.when(usuarioService.salvarUsuario(Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        SaidaCadastroDTO usuarioResposta = objectMapper.readValue(jsonResposta, SaidaCadastroDTO.class);

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuarioValidacaoNome() throws Exception {
        usuarioDTO.setNome("");
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuarioValidacaoEmail() throws Exception {
        usuarioDTO.setEmail("email");
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuarioValidacaoEmailNull() throws Exception {
        usuarioDTO.setEmail(null);
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuarioValidacaoEmailBlank() throws Exception {
        usuarioDTO.setEmail("");
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuarioValidacaoSenha() throws Exception {
        usuarioDTO.setSenha(null);
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarCadastroDeUsuarioValidacaoSenhaBlank() throws Exception {
        usuarioDTO.setSenha("");
        Mockito.when((usuarioService.salvarUsuario(Mockito.any(Usuario.class)))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    //testar cadastro de usuário que já existe
    @Test
    public void testarCadastroDeUsuarioQueJaExiste() throws Exception {
        Mockito.doThrow(UsuarioJaCadastradoException.class).when(usuarioService)
                .salvarUsuario(Mockito.any(Usuario.class));

        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders
                        .post("/usuarios").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(422));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarExibicaoDeUsuarios() throws Exception {
        Mockito.when(usuarioService.buscarUsuarios(Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(Arrays.asList(usuario));

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        List<UsuarioSaidaDTO> usuarios = objectMapper.readValue(jsonResposta,
                new TypeReference<List<UsuarioSaidaDTO>>() {
                });

    }

    //testar exibir usuario por id
    @Test
    @WithMockUser("user@user.com")
    public void testarExibicaoDeUsuario() throws Exception {

        Mockito.when(usuarioService.buscarUsuario(Mockito.anyString())).thenReturn(usuario);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        UsuarioSaidaDTO usuario = objectMapper.readValue(jsonResposta,
                UsuarioSaidaDTO.class);

    }

    //testar exibição de usuario que não existe
    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarExibirUsuarioNaoEncontrado() throws Exception {

        Mockito.doThrow(UsuarioNaoEncontradoException.class).when(usuarioService)
                .buscarUsuario(Mockito.anyString());

        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders
                        .get("/usuarios/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarAtualizarUsuario() throws Exception {
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn("1");
        Mockito.when(usuarioService.atualizarUsuario(Mockito.anyString(), Mockito.any(Usuario.class))).thenReturn(usuario);
        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.put("/usuarios")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        String jsonResposta = resultado.andReturn().getResponse().getContentAsString();
        UsuarioSaidaDTO usuarioResposta = objectMapper.readValue(jsonResposta, UsuarioSaidaDTO.class);

    }

    //testar atualizar usuario que não existe
    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarAtualizarUsuarioNaoPermitido() throws Exception {

        String json = objectMapper.writeValueAsString(usuarioDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders
                        .put("/usuarios/1").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(405));

    }

    @Test
    @WithMockUser("user@user.com")
    public void testarDeletarUsuario() throws Exception {
        Mockito.when(usuarioLogadoService.pegarId()).thenReturn("1");
        Mockito.doNothing().when(usuarioService).deletarusuario(Mockito.anyString());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));

        Mockito.verify(usuarioService, Mockito.times(1)).deletarusuario(Mockito.anyString());

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarDeletarOutroUsuario() throws Exception {
        Mockito.doThrow(UsuarioNaoEncontradoException.class).when(usuarioService).deletarusuario(Mockito.anyString());

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(405));

    }

    @Test //TDD
    @WithMockUser("user@user.com")
    public void testarAtualizarListaDeInteresses() throws Exception {

        Mockito.doNothing().when(usuarioService).atualizarListaDeInteresses(Mockito.anyInt()
                , Mockito.any(Operacao.class));

        String json = objectMapper.writeValueAsString(atualizarInteressesDTO);

        ResultActions resultado = mockMvc.perform(MockMvcRequestBuilders
                        .patch("/usuarios").content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

}
