package br.com.zup.projeto_final.Livro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends PagingAndSortingRepository <Livro, Integer> {

    @Query(value = "select * from livros l WHERE l.genero like %?1% AND l.tags like %?2% AND l.nome like %?3% AND l.autor like %?4%",
            nativeQuery = true)
    Page<Livro> aplicarTodosFiltros(String genero, String tags, String nome, String autor, Pageable pageable);

    @Query(value = "select * from livros l WHERE l.genero = ?1 AND l.nome like %?2% AND l.autor like %?3%",
            nativeQuery = true)
   Page <Livro> aplicarFiltroGenero(String genero, String nome, String autor, Pageable pageable);

    @Query(value = "select * from livros l WHERE l.tags like ?1 AND l.nome like %?2% AND l.autor like %?3%",
            nativeQuery = true)
    Page <Livro> aplicarFiltroTags(String tags, String nome, String autor, Pageable pageable);

    @Query(value = "select * from livros l WHERE l.nome like %?1% AND l.autor like %?2%",
            nativeQuery = true)
    Page<Livro> aplicarFiltroNomeEAutor(String nome, String autor, Pageable pageable);

    @Override
    Page<Livro> findAll(Pageable pageable);

    @Query(value = "select * from livros  WHERE nome_tratado = :nome AND autor_tratado = :autor",
            nativeQuery = true)
    Optional<Livro> buscarLivroPorNomeTratadoEAutorTratado(String nome, String autor);

}
