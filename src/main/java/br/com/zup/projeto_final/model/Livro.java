package br.com.zup.projeto_final.model;

import br.com.zup.projeto_final.enuns.Genero;
import br.com.zup.projeto_final.enuns.Tags;
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
    private String imagem;

}
