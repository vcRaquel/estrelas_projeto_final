package br.com.zup.projeto_final.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SaidaCadastroDTO {
    @NotBlank
    @Size(min = 2, message = "O nome não pode ter menos de 2 caracteres")
    private String nome;
    @Email
    @NotBlank
    private String email;
}
