package br.com.zup.projeto_final.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import br.com.zup.projeto_final.components.ConversorDeLivroComPaginacao;
import br.com.zup.projeto_final.enuns.Genero;
import br.com.zup.projeto_final.enuns.Tags;
import br.com.zup.projeto_final.dtos.LivroDTO;
import br.com.zup.projeto_final.service.LivroService;
import br.com.zup.projeto_final.model.Livro;
import br.com.zup.projeto_final.service.UsuarioLogadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/livros")
@Api(value = "Clube de leitura")
@CrossOrigin(origins = "*")
public class LivroController {

    @Autowired
    LivroService livroService;
    @Autowired
    UsuarioLogadoService usuarioLogado;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ConversorDeLivroComPaginacao conversorDeLivroComPaginacao;

    @PostMapping
    @ApiOperation(value = "Cadastrar Livro")
    @ResponseStatus(HttpStatus.CREATED)

    public LivroDTO cadastrarLivro(@Valid @RequestBody LivroDTO livroDTO) {
        Livro livro = modelMapper.map(livroDTO, Livro.class);
        livroService.salvarLivro(livro, usuarioLogado.pegarId());

        return modelMapper.map(livro, LivroDTO.class);
    }

    @GetMapping
    @ApiOperation(value = "Exibir Livros")
    public Page<LivroDTO> exibirTodosOsLivros(@RequestParam(required = false) Genero genero,
                                              @RequestParam(required = false) Tags tags,
                                              @RequestParam(required = false) String nome,
                                              @RequestParam(required = false) String autor,
                                              Pageable pageable) {

        if (nome == null){
            nome = "";
        }
        if (autor == null){
            autor = "";
        }

        var tarefas = livroService.exibirTodosOsLivros(genero, tags, nome, autor, pageable);
        List<LivroDTO> livrosDTO = conversorDeLivroComPaginacao.converterPaginaEmLista(tarefas);

        return new PageImpl<>(livrosDTO, pageable, livrosDTO.size());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Exibir Livro")
    @ResponseStatus(HttpStatus.OK)
    public LivroDTO exibirLivro(@PathVariable int id) {
        Livro livro  = livroService.buscarLivro(id);
        return modelMapper.map(livro, LivroDTO.class);

    }

    @PutMapping(path = {"/{id}"})
    @ApiOperation(value = "Atualizar Livro")
    @ResponseStatus(HttpStatus.OK)
    public LivroDTO atualizarLivro(@PathVariable int id, @RequestBody LivroDTO livroDTO) {
        Livro livro = livroService.atualizarLivro(id, modelMapper.map(livroDTO, Livro.class));
        return modelMapper.map(livro, LivroDTO.class);
    }

    @DeleteMapping(path = {"/{id}"})
    @ApiOperation(value = "Deletar Livro")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@PathVariable int id) {
        livroService.deletarLivro(id);
    }

}