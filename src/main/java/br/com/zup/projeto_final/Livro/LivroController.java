package br.com.zup.projeto_final.Livro;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroDTO cadastrarLivro(@RequestBody Livro livroDTO) {
        Livro livro = modelMapper.map(livroDTO, Livro.class);
        livroService.salvarLivro(livro);
        return livroDTO;
    }

    public List<LivroDTO> exibirLivros() {
        List<LivroDTO> livrosDTO = new ArrayList<>();

        for (Livro livro : livroService.buscarLivros()) {
            LivroDTO livroDTO = new LivroDTO(livro.getNome());
            livrosDTO.add(livroDTO);
        }

        return livrosDTO;
    }


}
