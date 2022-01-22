package br.com.zup.projeto_final.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> buscarUsuarios() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        return (List<Usuario>)usuarios ;
    }

}
