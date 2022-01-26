package br.com.zup.projeto_final.curtida;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curtida")
public class CurtidaController {

    @Autowired
    CurtidaService curtidaService;
    @Autowired
    ModelMapper modelMapper;

}
