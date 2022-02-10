package br.com.zup.projeto_final.dtos;

import br.com.zup.projeto_final.enuns.Genero;
import br.com.zup.projeto_final.enuns.Tags;
import br.com.zup.projeto_final.model.Review;
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
    private String imagem;

    public LivroDTO() {
    }

}
