package br.com.zup.projeto_final.Textos.comentario;

import br.com.zup.projeto_final.Usuario.Usuario;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
@Data
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Usuario quemComentou;
    private String texto;
    private int livro_id;

}
