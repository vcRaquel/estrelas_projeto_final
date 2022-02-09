package br.com.zup.projeto_final.dtos;

import br.com.zup.projeto_final.Enun.Operacao;
import lombok.Data;

@Data
public class AtualizarInteressesDTO {

    private int id_livro;
    private Operacao operacao;
}
