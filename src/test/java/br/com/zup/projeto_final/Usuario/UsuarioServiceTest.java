package br.com.zup.projeto_final.Usuario;

import br.com.zup.projeto_final.Usuario.customException.UsuarioJaCadastradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UsuarioServiceTest {
    @MockBean
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setId("1");
        usuario.setEmail("zupper@zup.com");
        usuario.setNome("Zupper");
        usuario.setSenha("aviao11");

    }

    @Test
    public void testarSalvarUsuario() {
        Mockito.when(usuarioRepository.save(usuario))
                .thenAnswer(objto -> objto.getArgument(0, Usuario.class));

        usuarioService.salvarUsuario(usuario);

        Mockito.verify(usuarioRepository, Mockito.times(1)).save(usuario);

    }

    //testar salvar usuário repetido
    @Test
    public void testarSalvarUsuarioRepetido(){
        Mockito.when(usuarioRepository.save(usuario))
                .thenThrow(new UsuarioJaCadastradoException("Usuário já cadastrado"));

        UsuarioJaCadastradoException exception = Assertions.assertThrows(UsuarioJaCadastradoException.class, () ->{
            usuarioService.salvarUsuario(usuario);
        });


    }


    //testar exibir usuários

    //testar exibir usuário

    //testar exibir usuário que não existe

    //testar atualizar usuário

    //testar atualizar usuário que não existe

    //testar deletar usuario

    //testar deletar usuário que não existe

}
