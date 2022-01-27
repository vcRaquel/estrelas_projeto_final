package br.com.zup.projeto_final.Livro;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Usuario.UsuarioService;
import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.customException.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public List<Livro> exibirTodosOsLivros(Genero genero, Tags tags, String nome) {

        if (genero != null) {
            return livroRepository.findAllByGenero(genero);
        }

        if (tags!=null) {
            return livroRepository.findAllByTags(tags);
        }

        if (nome!= null) {
            return livroRepository.findAllByNome(nome);
        }

        List<Livro> livros = (List<Livro>) livroRepository.findAll();
        return livros;

    }

    public Livro atualizarLivro(int id, Livro livro) {

        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não cadastrado.");
        }

        Livro livroParaAtualizar = livroOptional.get();
        // regra de negócio

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setGenero(livro.getGenero());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setTags(livro.getTags());

        livroRepository.save(livroParaAtualizar);

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setTags(livro.getTags());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setGenero(livro.getGenero());

        return livroParaAtualizar;
    }

    public void deletarLivro(int id) {
        livroRepository.deleteById(id);
    }

}
