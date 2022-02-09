package br.com.zup.projeto_final.repository;

import br.com.zup.projeto_final.model.Curtida;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CurtidaRepository extends CrudRepository<Curtida, Integer> {

    @Query(value = "SELECT * FROM curtidas c WHERE c.id_usuario = ?1 AND c.id_recurso = ?2 AND c.tipo = ?3",
            nativeQuery = true)
    Curtida curtidaRepetida(String id_usuario, int id_recurso, String tipo);
}
