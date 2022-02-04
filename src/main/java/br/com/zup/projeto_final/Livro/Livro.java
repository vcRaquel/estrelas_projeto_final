package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.review.Review;
import br.com.zup.projeto_final.Usuario.Usuario;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "livros")
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String nomeTratado;
    private String autor;
    private String autorTratado;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Enumerated(EnumType.STRING)
    private Tags tags;
    @ManyToOne
    private Usuario quemCadastrou;
    @OneToOne (cascade = CascadeType.PERSIST)
    private Review review;
    @OneToMany
    private List<Comentario> comentarios;
    private  boolean lido;

}
