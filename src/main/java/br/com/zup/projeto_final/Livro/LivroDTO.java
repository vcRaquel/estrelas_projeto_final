package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.review.Review;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LivroDTO {

    @NotBlank
    @Size(min = 2,message = "O nome do livro não pode ter menos de 2 caracteres")
    private String nome;
    @NotBlank
    @Size(min = 2,message = "O nome do Autor não pode ter menos de 2 caracteres")
    private String autor;
    @NotNull
    private Genero genero;
    private Tags tags;
    private Review review;
    private boolean lido;

    public LivroDTO() {
    }

}
