
package br.com.zup.projeto_final.Usuario;

import br.com.zup.projeto_final.Livro.Livro;

import br.com.zup.projeto_final.customException.UsuarioJaCadastradoException;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Usuario salvarUsuario(Usuario usuario) {
        if (usuarioExistePorEmail(usuario.getEmail())) {
            throw new UsuarioJaCadastradoException("Usuário já cadastrado");
        } else {
            String senhaEscondida = encoder.encode(usuario.getSenha());

            usuario.setSenha(senhaEscondida);
            usuarioRepository.save(usuario);
            return usuario;
        }

    }



    public List<Usuario> buscarUsuarios(String nomeUsuario, boolean orderByPontuacao) {
        List<Usuario> usuarios = new ArrayList<>();

        if (nomeUsuario == null & !orderByPontuacao){
            usuarios = (List<Usuario>) usuarioRepository.findAll();
        }
        else if (nomeUsuario == null){
            usuarios = usuarioRepository.findAllByOrderByPontuacaoDesc();
        }
        else if (!orderByPontuacao){
            usuarios = usuarioRepository.aplicarFiltroNome(nomeUsuario);
        }
        else{
            usuarios = usuarioRepository.aplicarFiltroNomeByOrderByPontuacaoDesc(nomeUsuario);
        }
        return usuarios;

    }

    public Usuario buscarUsuario(String id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isEmpty()) {
            return usuarioOptional.get();
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

    }

    public boolean usuarioExistePorEmail(String email) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (!usuarioOptional.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public Usuario atualizarUsuario(String id, Usuario usuario) {
        Usuario usuarioParaAtualizar = buscarUsuario(id);
        usuarioParaAtualizar.setNome(usuario.getNome());
        usuarioParaAtualizar.setEmail(usuario.getEmail());

        usuarioRepository.save(usuarioParaAtualizar);
        return usuarioParaAtualizar;

    }

    public void atualizarLivrosDoUsuario(String id, Livro livro){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()){
            throw new UsuarioNaoEncontradoException("Usuario não encontrado");
        }
        usuarioOptional.get().getLivrosCadastrados().add(livro);
        usuarioOptional.get().setPontuacao(usuarioOptional.get().getPontuacao() + 100);
        usuarioRepository.save(usuarioOptional.get());
    }


    public void deletarusuario(String id){
        Usuario usuarioQueEstaSendoDeletado = buscarUsuario(id);
        substituirUsuarioParaUsuarioDeletado(usuarioQueEstaSendoDeletado);
        usuarioRepository.deleteById(id);
    }

    public void substituirUsuarioParaUsuarioDeletado(Usuario usuario){
        List <Livro> livrosSemDono = usuario.getLivrosCadastrados();
        Optional <Usuario> usuarioDeletadoOptional = usuarioRepository.findByEmail("usuario_deletado@zupreaders.com");
        Usuario usuarioDeletado = usuarioDeletadoOptional.get();
        if (usuarioDeletado.getLivrosCadastrados() == null){
            usuarioDeletado.setLivrosCadastrados(new ArrayList<>());
        }

        for (Livro referencia : livrosSemDono){
            usuarioDeletado.getLivrosCadastrados().add(referencia);
        }
    }

}
