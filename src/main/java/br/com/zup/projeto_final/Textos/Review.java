package br.com.zup.projeto_final.Textos;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    private String texto;
    private  int curtidas;

}
