package br.com.zup.projeto_final.controller;

import br.com.zup.projeto_final.service.AutorService;
import br.com.zup.projeto_final.dtos.AutorDTO;
import br.com.zup.projeto_final.model.Autor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autores") // ver se é plural mesmo
public class AutorController {
    @Autowired
    AutorService autorService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutorDTO cadastrarAutor (@RequestBody AutorDTO autorDTO){
        Autor autor = modelMapper.map(autorDTO, Autor.class);
        autorService.salvarAutor(autor);
        return autorDTO;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AutorDTO> exibirAutores(){
        List<AutorDTO> autoresDTO = new ArrayList<>();

        for (Autor autor: autorService.buscarAutores()){
            AutorDTO autorDTO = new AutorDTO(autor.getNome());
            autoresDTO.add(autorDTO);
        }

        return autoresDTO;
    }

    @PutMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public AutorDTO atualizarAutor (@PathVariable int id, @RequestBody AutorDTO autorDTO){
        Autor autor = autorService.atualizarAutor(id, modelMapper.map(autorDTO, Autor.class));
        return autorDTO;
    }

    @DeleteMapping(path = {"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAutor (@PathVariable int id){
        autorService.deletarAutor(id);
    }

}
