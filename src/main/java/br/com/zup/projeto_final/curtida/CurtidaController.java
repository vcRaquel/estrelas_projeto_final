package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.curtida.dtos.CurtidaEntradaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

    @Autowired
    CurtidaService curtidaService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarCurtida (@RequestBody CurtidaEntradaDTO curtidaEntradaDTO){

        Curtida curtida = modelMapper.map(curtidaEntradaDTO, Curtida.class);
        curtidaService.salvarCurtida(curtida);

    }
}
