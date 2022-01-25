package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.Review;
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
    private String autor;
    private Genero genero;
    private Tags tags;
    @OneToOne (cascade = CascadeType.PERSIST)
    private Review review;
    @OneToMany
    private List<Comentario> comentario;
    private  boolean lido;
    private  int curtidas;

}
