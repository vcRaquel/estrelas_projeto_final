package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.Review;
import lombok.Data;

@Data
public class LivroDTO {

    private String nome;
    private String autor;
    private Genero genero;
    private Tags tags;
    private Review review;
    private boolean lido;

    public LivroDTO() {
    }

}
