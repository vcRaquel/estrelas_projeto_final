package br.com.zup.projeto_final.repository;

import br.com.zup.projeto_final.model.Comentario;
import org.springframework.data.repository.CrudRepository;

public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {
}
