package br.com.zup.projeto_final.Usuario;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    private String nome;
    private String email;
    private String senha;
    //private List<Livro> interesse
}
