package br.com.zup.projeto_final.Textos.comentario;

import br.com.zup.projeto_final.Usuario.UsuarioController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Comentarios")
public class ComentariosController {

    @Autowired
    ComentariosService comentariosService;
    @Autowired
    UsuarioController usuarioController;
    @Autowired
    ModelMapper modelMapper;



}
