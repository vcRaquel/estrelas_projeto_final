package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Autor.Autor;
import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.Comentario;
import br.com.zup.projeto_final.Textos.Review;
import lombok.Data;

@Data
public class LivroDTO {

    public class Livro {
        private String nome;
        //private Autor autores;
        private Genero genero;
        private Tags tags;
        //private Review review;
        //private Comentario comentario;
        private  boolean lido;
        private  int curtidas;
    }

    public LivroDTO() {
    }

}
