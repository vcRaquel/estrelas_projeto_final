package br.com.zup.projeto_final.Autor;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "autores")
@Data
public class Autor {
    private String nome;

}
