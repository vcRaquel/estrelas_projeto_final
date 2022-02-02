package br.com.zup.projeto_final.Textos.comentario;


import br.com.zup.projeto_final.Textos.comentario.dtos.ComentarioDTO;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    @Autowired
    ComentariosService comentariosService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UsuarioLogadoService usuarioLogadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarComentario(@RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        comentariosService.salvarComentario(usuarioLogadoService.pegarId(), comentario);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void exibirUsuario(@PathVariable int id) {
        Comentario usuario  = comentariosService.buscarComentario(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ComentarioDTO> exibirUsuarios() {

        List<ComentarioDTO> comentarios = new ArrayList<>();
        for (Comentario comentario : comentariosService.buscarComentarios()) {
            ComentarioDTO comentarioDTO = new ComentarioDTO(comentario.getTexto(), comentario.getLivro_id());
            comentarios.add(comentarioDTO);
        }
        return comentarios;
    }

}
