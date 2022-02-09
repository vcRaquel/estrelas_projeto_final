package br.com.zup.projeto_final.components;

import br.com.zup.projeto_final.model.Livro;
import br.com.zup.projeto_final.dtos.LivroDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ConversorDeLivroComPaginacao {

    @Autowired
    private ModelMapper modelMapper;

    public List<LivroDTO> converterPaginaEmLista(Page<Livro> livroService) {
        List<LivroDTO> livros = new ArrayList<>();
        for (Livro livro : livroService){
            LivroDTO livroDTO = modelMapper.map(livro, LivroDTO.class);
            livros.add(livroDTO);
        }
        return livros;
    }

}
