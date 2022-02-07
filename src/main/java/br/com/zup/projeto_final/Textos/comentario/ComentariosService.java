package br.com.zup.projeto_final.Textos.comentario;

import br.com.zup.projeto_final.Livro.Livro;
import br.com.zup.projeto_final.Livro.LivroService;
import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.AtualizacaoInvalidaException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.ComentarioNaoEncontradoException;
import br.com.zup.projeto_final.Textos.comentario.customExceptions.DelecaoInvalidaException;
import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.Usuario.UsuarioRepository;

import br.com.zup.projeto_final.Usuario.UsuarioService;
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
    UsuarioService usuarioService;
    @Autowired
    UsuarioLogadoService usuarioLogadoService;
    @Autowired
    UsuarioRepository usuarioRepository;


    public void salvarComentario(String idUsuario, Comentario comentario){
        Usuario usuario = usuarioService.buscarUsuario(idUsuario);
        comentario.setQuemComentou(usuario);
        comentarioRepository.save(comentario);
        pontuarUsuario(usuario, comentario.getLivro_id());
        livroService.atualizarComentariosDoLivro(comentario.getLivro_id(), comentario);

    }

    public void pontuarUsuario(Usuario usuario, int livro_id){
        Livro livro = livroService.buscarLivro(livro_id);
        boolean primeiroComentario = true;
        for (Comentario comentario : livro.getComentarios()){
            if (comentario.getQuemComentou() == usuario){
                primeiroComentario = false;
            }
        }
        if (primeiroComentario){
            usuario.setPontuacao(usuario.getPontuacao() + 20);
            usuarioRepository.save(usuario);
        }
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

    public void deletarComentario(int id){
        Usuario usuario = usuarioService.buscarUsuario(usuarioLogadoService.pegarId());
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);
        if (comentarioOptional.isEmpty()) {
            throw new ComentarioNaoEncontradoException("Comentario não cadastrado.");
        }
        Comentario comentarioParaDeletar = comentarioOptional.get();
        if (!comentarioParaDeletar.getQuemComentou().getId().equals(usuario.getId())){
            throw new DelecaoInvalidaException("Você só pode deletar os seus comentários");
        }
        Livro livro = livroService.buscarLivro(comentarioParaDeletar.getLivro_id());
        livro.getComentarios().remove(comentarioParaDeletar);
        comentarioRepository.delete(comentarioParaDeletar);
    }

}
