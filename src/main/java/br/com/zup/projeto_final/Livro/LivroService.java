package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.customException.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;

    public Livro salvarLivro(Livro livro) {
        livroRepository.save(livro);
        return livro;
    }

    public List<Livro> buscarLivros() {
        Iterable<Livro> livros = livroRepository.findAll();
        return (List<Livro>) livros;
    }

    public Livro atualizarLivro(int id, Livro livro) {

        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não cadastrado.");
        }

        Livro livroParaAtualizar = livroOptional.get();
        // regra de negócio

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setCurtidas(livro.getCurtidas());
        livroParaAtualizar.setGenero(livro.getGenero());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setTags(livro.getTags());

        livroRepository.save(livroParaAtualizar);

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setTags(livro.getTags());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setGenero(livro.getGenero());
        livroParaAtualizar.setCurtidas(livro.getCurtidas());


        return livroParaAtualizar;
    }

    public void deletarLivro(int id) {
        livroRepository.deleteById(id);
    }

}
