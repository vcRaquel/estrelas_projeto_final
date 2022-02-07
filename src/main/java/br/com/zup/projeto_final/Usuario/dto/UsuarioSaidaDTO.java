package br.com.zup.projeto_final.Usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioSaidaDTO {
    private String nome;
    private String email;
    private int pontuacao;
    //private List<Livro> interesse

    public UsuarioSaidaDTO() {

    }


}
