
package br.com.zup.projeto_final.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Usuario salvarUsuario(Usuario usuario) {
        String senhaEscondida = encoder.encode(usuario.getSenha());

        usuario.setSenha(senhaEscondida);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> buscarUsuarios() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        return (List<Usuario>)usuarios ;
    }

    public Usuario atualizarUsuario(String id, Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void deletarusuario(String id){
        usuarioRepository.deleteById(id);
    }

}
