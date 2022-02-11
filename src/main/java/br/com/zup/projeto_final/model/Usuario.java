package br.com.zup.projeto_final.model;

import br.com.zup.projeto_final.dtos.LivroDTO;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @OneToMany
    private List<Livro> listaDeInteresses;
    private int pontuacao;

}
