package br.com.zup.projeto_final.dtos;

import lombok.Data;

@Data
public class AutorDTO {
    private String nome;

    public AutorDTO(String nome) {
        this.nome = nome;
    }

    public AutorDTO() {

    }
}
