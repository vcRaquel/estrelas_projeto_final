package br.com.zup.projeto_final.model;

import br.com.zup.projeto_final.enuns.Genero;
import br.com.zup.projeto_final.enuns.Tags;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "livros")
@NoArgsConstructor
@Getter
@Setter
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
    @NotNull
    @OneToOne (cascade = CascadeType.PERSIST)
    private Review review;
    @OneToMany
    private List<Comentario> comentarios;
    private  boolean lido;
    private String imagem;

}
