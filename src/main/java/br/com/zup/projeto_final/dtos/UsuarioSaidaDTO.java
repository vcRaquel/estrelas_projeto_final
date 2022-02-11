package br.com.zup.projeto_final.dtos;

import br.com.zup.projeto_final.model.Livro;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioSaidaDTO {
    private String nome;
    private String email;
    private int pontuacao;
    private List<LivroDTO> interesse;

    public UsuarioSaidaDTO() {
    }

}
