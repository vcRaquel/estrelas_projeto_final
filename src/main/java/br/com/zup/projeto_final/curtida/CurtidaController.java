package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.curtida.dtos.CurtidaEntradaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curtida")
@Api(value = "Clube de leitura")
@CrossOrigin(origins = "*")
public class CurtidaController {

    @Autowired
    CurtidaService curtidaService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ApiOperation(value = "Salvar Curtida")
    @ResponseStatus(HttpStatus.CREATED)
    public void salvarCurtida (@RequestBody CurtidaEntradaDTO curtidaEntradaDTO){

        Curtida curtida = modelMapper.map(curtidaEntradaDTO, Curtida.class);
        curtidaService.salvarCurtida(curtida);

    }
}
