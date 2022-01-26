package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Usuario.UsuarioService;
import br.com.zup.projeto_final.customException.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    UsuarioService usuarioService;

    public Livro salvarLivro(Livro livro, String idUsuario) {
        livroRepository.save(livro);
        usuarioService.atualizarLivrosDoUsuario(idUsuario, livro);
        return livro;
    }

    public List<Livro> buscarLivros() {
        Iterable<Livro> livros = livroRepository.findAll();
        return (List<Livro>) livros;
    }

    public void atualizarComentariosDoLivro(int idLivro, Comentario comentario) {
        Optional<Livro> livroOptional = livroRepository.findById(idLivro);
        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado");
        }
        livroOptional.get().getComentarios().add(comentario);
        livroRepository.save(livroOptional.get());
    }

    public List<Livro> exibirTodosOsLivros(Genero genero, Tags tags, String nome, String autor) {

        if (genero != null) {
            return livroRepository.findAllByGenero(genero);
        }

        if (tags != null) {
            return livroRepository.findAllByTags(tags);
        }

        if (nome != null) {
            return livroRepository.findAllByNome(nome);
        }

        if (autor != null) {
            return livroRepository.findAllByAutor(autor);
        }

        List<Livro> livros = (List<Livro>) livroRepository.findAll();

        List<Livro> livrosOrdenados = new ArrayList<>();
        Livro livroMaisComentado = null;

        while (livros.size() != livrosOrdenados.size()) {

            for (Livro referencia : livros) {
                if (livroMaisComentado == null) {
                    livroMaisComentado = referencia;
                } else if (referencia.getComentarios().size() > livroMaisComentado.getComentarios().size()) {
                    livroMaisComentado = referencia;
                }
            }
            livrosOrdenados.add(livroMaisComentado);
            livroMaisComentado = null;
        }
        return livrosOrdenados;

    }

    public Livro buscarLivro(int id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        if (!livroOptional.isEmpty()) {
            return livroOptional.get();
        } else {
            throw new LivroNaoEncontradoException("Livro não encontrado");
        }

    }

    public Livro atualizarLivro(int id, Livro livro) {

        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não cadastrado.");
        }

        Livro livroParaAtualizar = livroOptional.get();

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setGenero(livro.getGenero());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setTags(livro.getTags());

        livroRepository.save(livroParaAtualizar);

        return livroParaAtualizar;
    }

    public void deletarLivro(int id) {
        buscarLivro(id);
        livroRepository.deleteById(id);
    }

}
