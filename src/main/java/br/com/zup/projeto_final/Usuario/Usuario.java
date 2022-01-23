package br.com.zup.projeto_final.Usuario;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String email;
    private String senha;
    //private List<Livro> interesse

}
