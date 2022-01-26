package br.com.zup.projeto_final.Textos.comentario;

import br.com.zup.projeto_final.Livro.Livro;
import br.com.zup.projeto_final.Livro.LivroDTO;
import br.com.zup.projeto_final.Textos.comentario.dtos.ComentarioDTO;
import br.com.zup.projeto_final.Usuario.UsuarioController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    @Autowired
    ComentariosService comentariosService;
    @Autowired
    UsuarioController usuarioController;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        comentariosService.salvarComentario(usuarioController.pegarId(), comentario.getIdLivro(), comentario);

    }

}
