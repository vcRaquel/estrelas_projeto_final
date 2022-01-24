package br.com.zup.projeto_final.Textos;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
@Data
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String texto;
    private  int curtidas;

}
