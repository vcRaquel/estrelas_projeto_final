package br.com.zup.projeto_final.Textos.comentario;

import br.com.zup.projeto_final.Usuario.Usuario;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comentarios")
@Data
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Usuario quemComentou;
    @NotBlank
    private String texto;
    @NotNull
    private int livro_id;
    private int curtidas;

}
