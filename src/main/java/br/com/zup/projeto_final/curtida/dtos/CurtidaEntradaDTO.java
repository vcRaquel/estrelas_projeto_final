package br.com.zup.projeto_final.curtida.dtos;

import br.com.zup.projeto_final.Enun.Tipo;
import lombok.Data;

@Data
public class CurtidaEntradaDTO {

    private Long id_usuario;
    private Long id_recurso;
    private Tipo tipo;

}
