package br.com.zup.projeto_final.Usuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")//verificar se é plural mesmo
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ModelMapper modelMapper;

}
