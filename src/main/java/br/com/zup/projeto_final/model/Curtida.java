package br.com.zup.projeto_final.model;


import br.com.zup.projeto_final.enuns.Tipo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "curtidas")
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_curtida;
    private int id_recurso;
    private String id_usuario;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

}
