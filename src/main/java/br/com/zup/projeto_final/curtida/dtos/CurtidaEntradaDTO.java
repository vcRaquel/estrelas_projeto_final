package br.com.zup.projeto_final.curtida.dtos;

import br.com.zup.projeto_final.Enun.Tipo;
import lombok.Data;

@Data
public class CurtidaEntradaDTO {

    private int id_usuario;
    private int id_recurso;
    private Tipo tipo;

}
