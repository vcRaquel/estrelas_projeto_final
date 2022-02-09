package br.com.zup.projeto_final.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.zup.projeto_final.service.ComentariosService;
import br.com.zup.projeto_final.dtos.ComentarioDTO;
import br.com.zup.projeto_final.model.Comentario;
import br.com.zup.projeto_final.service.UsuarioLogadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comentarios")
@Api(value = "Clube de leitura")
@CrossOrigin(origins = "*")
public class ComentariosController {

    @Autowired
    ComentariosService comentariosService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UsuarioLogadoService usuarioLogadoService;

    @PostMapping
    @ApiOperation(value = "Cadastrar Comentário")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarComentario(@RequestBody @Valid ComentarioDTO comentarioDTO) {
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        comentariosService.salvarComentario(usuarioLogadoService.pegarId(), comentario);

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Exibir Comentário")
    @ResponseStatus(HttpStatus.OK)
    public ComentarioDTO exibirComentario(@PathVariable int id) {
        Comentario comentario  = comentariosService.buscarComentario(id);
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @GetMapping
    @ApiOperation(value = "Exibir Comentários")
    @ResponseStatus(HttpStatus.OK)
    public List<ComentarioDTO> exibirComentarios() {

        List<ComentarioDTO> comentarios = new ArrayList<>();
        for (Comentario comentario : comentariosService.buscarComentarios()) {
            ComentarioDTO comentarioDTO = new ComentarioDTO(comentario.getTexto(), comentario.getLivro_id()
                    , comentario.getCurtidas());
            comentarios.add(comentarioDTO);
        }
        return comentarios;
    }

    @PutMapping(path = {"/{id}"})
    @ApiOperation(value = "Atualizar Comentário")
    @ResponseStatus(HttpStatus.OK)
    public ComentarioDTO atualizarComentario(@PathVariable int id, @Valid @RequestBody ComentarioDTO comentarioDTO) {
        Comentario comentario = comentariosService.atualizarComentario(id, modelMapper.map(comentarioDTO, Comentario.class));
        return modelMapper.map(comentario, ComentarioDTO.class);
    }

    @DeleteMapping(path = {"/{id}"})
    @ApiOperation(value = "Deletar Comentário")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarComentario(@PathVariable int id) {
        comentariosService.deletarComentario(id);
    }


}
