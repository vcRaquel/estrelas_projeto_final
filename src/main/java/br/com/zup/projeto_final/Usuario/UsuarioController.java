package br.com.zup.projeto_final.Usuario;
import br.com.zup.projeto_final.Usuario.dto.UsuarioDTO;
import br.com.zup.projeto_final.Usuario.dto.UsuarioSaidaDTO;
import br.com.zup.projeto_final.seguranca.UsuarioLogado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/usuarios")//verificar se é plural mesmo
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioSaidaDTO cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuarioService.salvarUsuario(usuario);
        return modelMapper.map(usuario, UsuarioSaidaDTO.class);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioSaidaDTO> exibirUsuarios() {

        List<UsuarioSaidaDTO> usuarios = new ArrayList<>();

        for (Usuario usuario : usuarioService.buscarUsuarios()) {
            UsuarioSaidaDTO usuarioSaidaDTO = new UsuarioSaidaDTO(usuario.getNome(), usuario.getEmail());
            usuarios.add(usuarioSaidaDTO);
        }


        return usuarios;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioSaidaDTO exibirUsuario(@PathVariable String id) {
        Usuario usuario =usuarioService.buscarUsuario(id);
        return modelMapper.map(usuario, UsuarioSaidaDTO.class);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioSaidaDTO atualizarUsuario(@PathVariable String id, @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioService.atualizarUsuario(id, modelMapper.map(usuarioDTO, Usuario.class));


        return modelMapper.map(usuario, UsuarioSaidaDTO.class);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarUsuario(@PathVariable String id) {
        usuarioService.deletarusuario(id);
    }


}
