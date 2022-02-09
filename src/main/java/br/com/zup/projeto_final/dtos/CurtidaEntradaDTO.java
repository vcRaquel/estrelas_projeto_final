package br.com.zup.projeto_final.dtos;

import br.com.zup.projeto_final.enuns.Tipo;
import lombok.Data;

@Data
public class CurtidaEntradaDTO {

    private String id_usuario;
    private int id_recurso;
    private Tipo tipo;

}
