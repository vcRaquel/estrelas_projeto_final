package br.com.zup.projeto_final.Autor;

import br.com.zup.projeto_final.Autor.customException.AutorNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    public void salvarAutor(Autor autor){
        autorRepository.save(autor);
    }

}
