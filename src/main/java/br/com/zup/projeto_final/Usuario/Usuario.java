package br.com.zup.projeto_final.Usuario;

import br.com.zup.projeto_final.Livro.Livro;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String nome;
    private String email;
    private String senha;
    @OneToMany
    private List<Livro> livrosCadastrados;
    private int pontuacao;

}
