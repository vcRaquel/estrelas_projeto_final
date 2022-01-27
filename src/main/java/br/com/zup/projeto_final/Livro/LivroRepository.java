package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import org.springframework.data.repository.CrudRepository;

import javax.naming.Name;
import java.util.List;

public interface LivroRepository extends CrudRepository <Livro, Integer> {
    List <Livro> findAllByGenero (Genero genero);
    List<Livro> findAllByTags (Tags tags);
    List<Livro> findAllByNome(String nome);

}
