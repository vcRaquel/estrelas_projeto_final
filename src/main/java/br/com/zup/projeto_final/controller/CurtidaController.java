package br.com.zup.projeto_final.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.zup.projeto_final.service.CurtidaService;
import br.com.zup.projeto_final.dtos.CurtidaEntradaDTO;
import br.com.zup.projeto_final.model.Curtida;
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
