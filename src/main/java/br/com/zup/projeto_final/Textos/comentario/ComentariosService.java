package br.com.zup.projeto_final.Textos.comentario;

import br.com.zup.projeto_final.Livro.Livro;
import br.com.zup.projeto_final.Livro.LivroService;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.AtualizacaoInvalidaException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.ComentarioNaoEncontradoException;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioRepository;

import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentariosService {

    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    LivroService livroService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioLogadoService usuarioLogadoService;


    public void salvarComentario(String idUsuario, Comentario comentario){
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isEmpty()){
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");

        }
        comentarioRepository.save(comentario);
        livroService.atualizarComentariosDoLivro(comentario.getLivro_id(), comentario);

        comentario.setQuemComentou(usuario.get());

    }

    public List<Comentario> buscarComentarios() {
        Iterable<Comentario> comentarios = comentarioRepository.findAll();
        return (List<Comentario>) comentarios;
    }

    public Comentario buscarComentario(int id) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
        if (!comentarioOptional.isEmpty()) {
            return comentarioOptional.get();
        } else {
            throw new ComentarioNaoEncontradoException("Comentario não encontrado");
        }

    }

    public Comentario atualizarComentario(int id, Comentario comentario) {

        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
        if (comentarioOptional.isEmpty()) {
            throw new ComentarioNaoEncontradoException("Comentario não cadastrado.");
        }
        Comentario comentarioParaAtualizar = comentarioOptional.get();
        if (!comentarioParaAtualizar.getQuemComentou().getId().equals(usuarioLogadoService.pegarId())){
            throw new AtualizacaoInvalidaException("Você só pode atualizar os seus comentários");
        }
        comentarioParaAtualizar.setTexto(comentario.getTexto());
        comentarioRepository.save(comentarioParaAtualizar);

        return comentarioParaAtualizar;
    }

}
