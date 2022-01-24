package br.com.zup.projeto_final.Livro;

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
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroDTO cadastrarLivro(@RequestBody Livro livroDTO) {
        Livro livro = modelMapper.map(livroDTO, Livro.class);
        livroService.salvarLivro(livro);

        return modelMapper.map(livro, LivroDTO.class);
    }

    @GetMapping
    public List<LivroDTO> exibirLivros() {
        List<LivroDTO> livrosDTO = new ArrayList<>();

        for (Livro livro : livroService.buscarLivros()) {
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