package br.com.zup.projeto_final.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    @NotBlank
    private String texto;
    @NotNull
    private Integer livro_id;
    private int curtidas;

}
