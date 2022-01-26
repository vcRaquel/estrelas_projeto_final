package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LivroRepository extends CrudRepository <Livro, Integer> {
    List <Livro> findAllByGenero (Genero genero);

}
