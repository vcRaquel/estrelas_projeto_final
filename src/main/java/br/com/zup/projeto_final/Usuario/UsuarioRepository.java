
package br.com.zup.projeto_final.Usuario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    @Query(value = "select * from usuarios l WHERE l.nome like %?1%",
            nativeQuery = true)
    List<Usuario> aplicarFiltroNome(String nome);


}
