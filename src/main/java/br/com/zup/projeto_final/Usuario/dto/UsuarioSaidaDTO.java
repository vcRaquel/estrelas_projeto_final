package br.com.zup.projeto_final.Usuario.dto;

import lombok.Data;

@Data
public class UsuarioSaidaDTO {
    private String nome;
    private String email;
    //private List<Livro> interesse

    public UsuarioSaidaDTO() {

    }

    public UsuarioSaidaDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

}
