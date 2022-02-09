package br.com.zup.projeto_final.service;

import br.com.zup.projeto_final.customException.AutorNaoEncontradoException;
import br.com.zup.projeto_final.model.Autor;
import br.com.zup.projeto_final.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    AutorRepository autorRepository;

    public Autor salvarAutor(Autor autor){
        autorRepository.save(autor);
        return autor;
    }

    public List<Autor> buscarAutores(){
        Iterable<Autor> autores = autorRepository.findAll();
        return (List<Autor>)autores;
    }

    public Autor atualizarAutor(int id, Autor autor){
        Optional<Autor> autorOptional = autorRepository.findById(id);

        if (autorOptional.isEmpty()){
            throw new AutorNaoEncontradoException("Autor não cadastrado, por gentileza efetue o cadastro primeiro.");

        }
        Autor autorParaAtualizar = autorOptional.get();
        //regra do que pode ser atualizado no Autor

        autorRepository.save(autorParaAtualizar);
        return autorParaAtualizar;
    }

    public void deletarAutor(int id){
        autorRepository.deleteById(id);
    }

}
