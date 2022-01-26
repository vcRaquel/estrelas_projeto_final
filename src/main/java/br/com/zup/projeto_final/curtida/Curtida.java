package br.com.zup.projeto_final.curtida;


import br.com.zup.projeto_final.Enun.Tipo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int id_recurso;
    private int id_usuario;
    private Tipo tipo;

}
