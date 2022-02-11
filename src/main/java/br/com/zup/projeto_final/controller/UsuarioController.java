
package br.com.zup.projeto_final.controller;
import br.com.zup.projeto_final.dtos.*;
import br.com.zup.projeto_final.model.Livro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.zup.projeto_final.service.UsuarioService;
import br.com.zup.projeto_final.model.Usuario;
import br.com.zup.projeto_final.service.UsuarioLogadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/usuarios")
@Api(value = "Clube de leitura")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UsuarioLogadoService usuarioLogadoService;


    @PostMapping
    @ApiOperation(value = "Cadastrar Usuário")
    @ResponseStatus(HttpStatus.CREATED)
    public SaidaCadastroDTO cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuarioService.salvarUsuario(usuario);
        return modelMapper.map(usuario, SaidaCadastroDTO.class);
    }

    @GetMapping
    @ApiOperation(value = "Exibir Usuários")
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioSaidaDTO> exibirUsuarios(@RequestParam(required = false) String nomeUsuario,
                                                @RequestParam(required = false) boolean orderByPontuacao) {
        System.out.println(nomeUsuario);
        List<UsuarioSaidaDTO> usuarios = new ArrayList<>();
        List<LivroDTO> listaDeInteressesDTO = new ArrayList<>();

        for (Usuario usuario : usuarioService.buscarUsuarios(nomeUsuario, orderByPontuacao)) {
            for (Livro livro : usuario.getListaDeInteresses()){
                LivroDTO livroDTO = modelMapper.map(livro, LivroDTO.class);
                listaDeInteressesDTO.add(livroDTO);
            }
            UsuarioSaidaDTO usuarioSaidaDTO = new UsuarioSaidaDTO(usuario.getNome(), usuario.getEmail(),
                    usuario.getPontuacao(), listaDeInteressesDTO);
            usuarios.add(usuarioSaidaDTO);
        }
        return usuarios;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Exibir Usuário")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioSaidaDTO exibirUsuario(@PathVariable String id) {
        Usuario usuario =usuarioService.buscarUsuario(id);
        return modelMapper.map(usuario, UsuarioSaidaDTO.class);

    }

    @PutMapping
    @ApiOperation(value = "Atualizar Usuário")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioSaidaDTO atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioService.atualizarUsuario(usuarioLogadoService.pegarId(),
                modelMapper.map(usuarioDTO, Usuario.class));

        return modelMapper.map(usuario, UsuarioSaidaDTO.class);
    }

    @PatchMapping
    public void atualizarListaDeInteresses(@RequestBody AtualizarInteressesDTO atualizarInteressesDTO){

        usuarioService.atualizarListaDeInteresses(atualizarInteressesDTO.getId_livro(),
                atualizarInteressesDTO.getOperacao());
    }

    @DeleteMapping
    @ApiOperation(value = "Deletar Usuário")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario() {
        usuarioService.deletarusuario(usuarioLogadoService.pegarId());
    }



}
