package br.com.zup.projeto_final.Livro;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface LivroRepository extends CrudRepository <Livro, Integer> {

    @Query(value = "select * from livros l WHERE l.genero like %?1% AND l.tags like %?2% AND l.nome like %?3% AND l.autor like %?4%",
            nativeQuery = true)
    List <Livro> aplicarTodosFiltros(String genero, String tags, String nome, String autor);

    @Query(value = "select * from livros l WHERE l.genero = ?1 AND l.nome like %?2% AND l.autor like %?3%",
            nativeQuery = true)
    List <Livro> aplicarFiltroGenero(String genero, String nome, String autor);

    @Query(value = "select * from livros l WHERE l.tags like ?1 AND l.nome like %?2% AND l.autor like %?3%",
            nativeQuery = true)
    List <Livro> aplicarFiltroTags(String tags, String nome, String autor);

    @Query(value = "select * from livros l WHERE l.nome like %?1% AND l.autor like %?2%",
            nativeQuery = true)
    List<Livro> aplicarFiltroNomeEAutor(String nome, String autor);

}
