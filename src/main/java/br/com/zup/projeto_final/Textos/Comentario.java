package br.com.zup.projeto_final.Textos;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comentarios")
@Data
public class Comentario {
    private String texto;
    private  int curtidas;

}
