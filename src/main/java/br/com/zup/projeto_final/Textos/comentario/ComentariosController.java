package br.com.zup.projeto_final.Textos.comentario;


import br.com.zup.projeto_final.Livro.Livro;
import br.com.zup.projeto_final.Livro.LivroDTO;
import br.com.zup.projeto_final.Textos.comentario.dtos.ComentarioDTO;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public void cadastrarComentario(@RequestBody @Valid ComentarioDTO comentarioDTO) {
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        comentariosService.salvarComentario(usuarioLogadoService.pegarId(), comentario);

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ComentarioDTO exibirUsuario(@PathVariable int id) {
        Comentario comentario  = comentariosService.buscarComentario(id);
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ComentarioDTO> exibirUsuarios() {

        List<ComentarioDTO> comentarios = new ArrayList<>();
        for (Comentario comentario : comentariosService.buscarComentarios()) {
            ComentarioDTO comentarioDTO = new ComentarioDTO(comentario.getTexto(), comentario.getLivro_id()
                    , comentario.getCurtidas());
            comentarios.add(comentarioDTO);
        }
        return comentarios;
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ComentarioDTO atualizarComentario(@PathVariable int id, @Valid @RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = comentariosService.atualizarComentario(id, modelMapper.map(comentarioDTO, Comentario.class));
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarComentario(@PathVariable int id) {
        comentariosService.deletarComentario(id);
    }


}
