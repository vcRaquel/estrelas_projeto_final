package br.com.zup.projeto_final.curtida;


import br.com.zup.projeto_final.Enun.Tipo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "curtidas")
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_curtida;
    private Long id_recurso;
    private String id_usuario;
    private Tipo tipo;

}
