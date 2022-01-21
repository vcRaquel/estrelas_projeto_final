package br.com.zup.projeto_final.Autor;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "autores")
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;

}
