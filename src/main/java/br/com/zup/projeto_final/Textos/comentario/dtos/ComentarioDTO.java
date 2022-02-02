package br.com.zup.projeto_final.Textos.comentario.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    private String texto;
    private int livro_id;

}
