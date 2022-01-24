package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Autor.Autor;
import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.Comentario;
import br.com.zup.projeto_final.Textos.Review;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "livros")
@Data
public class Livro {
    @Id
    private int id;
    private String nome;
    private Autor autores;
    private Genero genero;
    private Tags tags;
    private Review review;
    private Comentario comentario;
    private  boolean lido;
    private  int curtidas;


}
