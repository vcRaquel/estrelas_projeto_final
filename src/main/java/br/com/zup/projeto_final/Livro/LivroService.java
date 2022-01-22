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

    public Livro salvarLivro (Livro livro) {
        livroRepository.save(livro);
        return livro;
    }

    public List<Livro> buscarLivros (){
        Iterable<Livro> livros = livroRepository.findAll();
        return (List<Livro>) livros;
    }

    public Livro atualizarLivro (String nome, Livro livro){
        Optional<Livro> livroOptional = livroRepository.findById(nome);

        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não cadastrado.");
        }

        Livro livroParaAtualizar = livroOptional.get();
        livroRepository.save(livroParaAtualizar);
        return livroParaAtualizar;
    }


}
