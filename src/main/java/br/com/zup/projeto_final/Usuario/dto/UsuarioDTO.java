package br.com.zup.projeto_final.Usuario.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UsuarioDTO {
    @NotBlank
    @Size(min = 2,message = "O nome não pode ter menos de 2 caracteres")
    private String nome;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String senha;
    //private List<Livro> interesse

}
