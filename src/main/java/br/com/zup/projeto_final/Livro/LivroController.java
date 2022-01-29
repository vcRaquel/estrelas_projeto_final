package br.com.zup.projeto_final.Livro;


import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.usuarioLogado.UsuarioLogadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    LivroService livroService;
    @Autowired
    UsuarioLogadoService usuarioLogado;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroDTO cadastrarLivro(@RequestBody LivroDTO livroDTO) {
        Livro livro = modelMapper.map(livroDTO, Livro.class);
        livroService.salvarLivro(livro, usuarioLogado.pegarId());

        return modelMapper.map(livro, LivroDTO.class);
    }

    /*@GetMapping
    public List<LivroDTO> exibirLivros() {
        List<LivroDTO> livrosDTO = new ArrayList<>();

        for (Livro livro : livroService.buscarLivros()) {
            LivroDTO livroDTO = modelMapper.map(livro, LivroDTO.class);
            livrosDTO.add(livroDTO);
        }

        return livrosDTO;
    }*/

    @GetMapping
    public List<LivroDTO> exibirTodosOsLivros(@RequestParam(required = false) Genero genero,
                                              @RequestParam(required = false) Tags tags,
                                              @RequestParam(required = false) String nome,
                                              @RequestParam(required = false) String autor) {

        List<LivroDTO> livrosDTO = new ArrayList<>();

        for (Livro livro : livroService.exibirTodosOsLivros(genero, tags, nome, autor)) {
            LivroDTO livroDTO = modelMapper.map(livro, LivroDTO.class);
            livrosDTO.add(livroDTO);
        }

        return livrosDTO;
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public LivroDTO atualizarLivro(@PathVariable int id, @RequestBody LivroDTO livroDTO) {
        Livro livro = livroService.atualizarLivro(id, modelMapper.map(livroDTO, Livro.class));
        return modelMapper.map(livro, LivroDTO.class);
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@PathVariable int id) {
        livroService.deletarLivro(id);
    }

}
