package br.com.zup.projeto_final.Usuario;


import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Livro.Livro;
import br.com.zup.projeto_final.customException.UsuarioJaCadastradoException;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UsuarioServiceTest {
    @MockBean
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioService usuarioService;

    private Usuario usuario;
    private Usuario usuarioDeletado;
    private List<Livro> livrosSemDono;
    private Livro livro;

    @BeforeEach
    public void setup() {

        livro = new Livro();
        livro.setNome("Livro");
        livro.setGenero(Genero.AVENTURA);
        livro.setId(1);
        livro.setLido(true);
        livro.setTags(Tags.LEITURA_LEVE);
        livro.setAutor("Autor");

        livrosSemDono = new ArrayList<>();
        livrosSemDono.add(livro);

        usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("zupper@zup.com");
        usuario.setNome("Zupper");
        usuario.setSenha("aviao11");
        usuario.setLivrosCadastrados(new ArrayList<>());
        usuario.getLivrosCadastrados().add(livro);


        usuarioDeletado = new Usuario();
        usuarioDeletado.setId("2");
        usuarioDeletado.setNome("USUARIO DELETADO");
        usuarioDeletado.setEmail("usuario_deletado@zupreaders.com");
        usuario.setSenha("usuario_deletado");


    }

    @Test
    public void testarUsuarioExistePorEmail() {
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Boolean resposta = usuarioService.usuarioExistePorEmail(Mockito.anyString());
        Assertions.assertTrue(resposta);

        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        resposta = usuarioService.usuarioExistePorEmail(Mockito.anyString());
        Assertions.assertFalse(resposta);

    }

    @Test
    public void testarSalvarUsuario() {
        //caso o usuário esteja cadastrado
        Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));
        Assertions.assertThrows(UsuarioJaCadastradoException.class, () -> usuarioService.salvarUsuario(usuario));

        //caso o usuário não esteja cadastrado
        Mockito.when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.empty());
        Mockito.when(usuarioRepository.save(usuario))
                .thenAnswer(objto -> objto.getArgument(0, Usuario.class));

        Usuario usuarioResposta = usuarioService.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);
        Assertions.assertNotNull(usuarioResposta);

    }

    //testar salvar usuário repetido
    @Test
    public void testarSalvarUsuarioRepetido() {
        Mockito.when(usuarioRepository.save(usuario))
                .thenThrow(new UsuarioJaCadastradoException("Usuário já cadastrado"));

        UsuarioJaCadastradoException exception = Assertions.assertThrows(UsuarioJaCadastradoException.class, () -> {
            usuarioService.salvarUsuario(usuario);
        });

    }

    //testar exibir usuários
    @Test
    public void testarBuscarUsuarios() {
        List<Usuario> usuarios = Arrays.asList(usuario);
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        List<Usuario> usuariosResposta = usuarioService.buscarUsuarios();
        Mockito.verify(usuarioRepository, Mockito.times(1)).findAll();
    }

    //testar exibir usuário
    @Test
    public void testarBuscarUsuario() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Usuario usuarioResposta = usuarioService.buscarUsuario(Mockito.anyString());
        Assertions.assertNotNull(usuarioResposta);
        Assertions.assertEquals(Usuario.class, usuarioResposta.getClass());
        Assertions.assertEquals(usuario.getId(), usuarioResposta.getId());
        Mockito.verify(usuarioRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> usuarioService.buscarUsuario(Mockito.anyString()));
        Mockito.verify(usuarioRepository, Mockito.times(2)).findById(Mockito.anyString());

    }

    //testar exibir usuário que não existe
    @Test
    public void testarExibirUsuarioNaoEncontrado() {
        Mockito.when(usuarioRepository.save(Mockito.any()))
                .thenReturn(usuario);

        Mockito.when(usuarioRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.buscarUsuario("0");
        });

        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());

    }

    //testar atualizar usuário
    @Test
    public void testarAtualizarUsuario() {

        Mockito.when(usuarioRepository.save(Mockito.any()))
                .thenReturn(usuario);

        Mockito.when(usuarioRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(usuario));

        usuarioService.atualizarUsuario(Mockito.anyString(), usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(Mockito.any());

    }

    //testar atualizar usuário que não existe
    @Test
    public void testarAtualizarUsuarioNaoEncontrado() {
        Mockito.when(usuarioRepository.save(Mockito.any()))
                .thenReturn(usuario);

        Mockito.when(usuarioRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.atualizarUsuario("0", usuario);
        });

        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());

    }

    //testar deletar usuario
    @Test
    public void testarDeletarUsuarioSucesso() {
        Mockito.when(usuarioRepository.findById(Mockito.anyString())).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuarioDeletado));
        Mockito.doNothing().when(usuarioRepository).deleteById(Mockito.anyString());
        usuarioService.deletarusuario(usuario.getId());

        Mockito.verify(usuarioRepository, Mockito.times(1)).deleteById(Mockito.anyString());

    }

    //testar deletar usuário que não existe
    @Test
    public void testarDeletarUsuarioNaoEncontrado() {
        Mockito.doNothing().when(usuarioRepository).deleteById(Mockito.anyString());

        UsuarioNaoEncontradoException exception = Assertions.assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.deletarusuario("0");
        });

        Assertions.assertEquals("Usuário não encontrado", exception.getMessage());
    }

}

