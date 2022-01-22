package br.com.zup.projeto_final.Livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
