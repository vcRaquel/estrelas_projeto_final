package br.com.zup.projeto_final.Autor;

import br.com.zup.projeto_final.Autor.dto.AutorDTO;
import br.com.zup.projeto_final.Components.ConversorModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autores") // ver se é plural mesmo
public class AutorController {
    @Autowired
    AutorService autorService;
    @Autowired
    ModelMapper modelMapper;


}
