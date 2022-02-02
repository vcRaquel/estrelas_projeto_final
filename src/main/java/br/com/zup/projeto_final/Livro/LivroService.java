package br.com.zup.projeto_final.Livro;

import br.com.zup.projeto_final.Components.TratarString;
import br.com.zup.projeto_final.Enun.Genero;
import br.com.zup.projeto_final.Enun.Tags;
import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Usuario.UsuarioService;

import br.com.zup.projeto_final.Livro.customException.LivroNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    TratarString tratarString;

    public boolean livroExistePorNome(String nomeLivro, String nomeAutor){
        String nomeTratadoLivro = tratarString.tratarString(nomeLivro);
        String nomeTratadoAutor = tratarString.tratarString(nomeAutor);

        Optional<Livro> livroOptional = livroRepository.buscarLivroPorNomeTratadoEAutorTratado
                (nomeTratadoLivro, nomeTratadoAutor);

        if (!livroOptional.isEmpty()){
            return true;
        }else{
            return false;
        }
    }


    public Livro salvarLivro(Livro livro, String idUsuario) {
        livroRepository.save(livro);
        usuarioService.atualizarLivrosDoUsuario(idUsuario, livro);
        return livro;
    }

    public List<Livro> buscarLivros() {
        Iterable<Livro> livros = livroRepository.findAll();
        return (List<Livro>) livros;
    }

    public void atualizarComentariosDoLivro(int idLivro, Comentario comentario) {
        Optional<Livro> livroOptional = livroRepository.findById(idLivro);
        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não encontrado");
        }
        livroOptional.get().getComentarios().add(comentario);
        livroRepository.save(livroOptional.get());
    }




    public List<Livro> exibirTodosOsLivros(Genero genero, Tags tags, String nome, String autor) {
        List<Livro> listaFiltrada = aplicarFiltros(genero, tags, nome, autor);
        return ordenarLista(listaFiltrada);

    }

    public List<Livro> aplicarFiltros(Genero genero, Tags tags, String nome, String autor){

        List<Livro> livros = new ArrayList<>();

        if (genero == null & tags == null & autor.equals("") & nome.equals("")) {
            livros = (List<Livro>) livroRepository.findAll();
        }else if (genero == null & tags == null){
            livros = livroRepository.aplicarFiltroNomeEAutor(nome, autor);
        }else if (genero == null) {
            livros = livroRepository.aplicarFiltroTags(String.valueOf(tags), nome, autor);
        }else if (tags == null) {
            livros = livroRepository.aplicarFiltroGenero(String.valueOf(genero), nome, autor);
        } else if (!autor.equals("") | nome.equals("")){
            livros = livroRepository.aplicarTodosFiltros(String.valueOf(genero), String.valueOf(tags), nome, autor);
        }

        return livros;

    }

    public List <Livro> ordenarLista(List<Livro> listaFiltrada){

        List<Livro> livrosOrdenados = new ArrayList<>();
        Livro livroMaisComentado = null;
        int qtdLivros = listaFiltrada.size();

        while (qtdLivros != livrosOrdenados.size()) {
            for (Livro referencia : listaFiltrada) {
                if (livroMaisComentado == null) {
                    livroMaisComentado = referencia;
                } else if (referencia.getComentarios().size() > livroMaisComentado.getComentarios().size()) {
                    livroMaisComentado = referencia;
                }
            }
            livrosOrdenados.add(livroMaisComentado);
            listaFiltrada.remove(livroMaisComentado);
            livroMaisComentado = null;
        }
        return livrosOrdenados;
    }

    public Livro buscarLivro(int id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        if (!livroOptional.isEmpty()) {
            return livroOptional.get();
        } else {
            throw new LivroNaoEncontradoException("Livro não encontrado");
        }

    }

    public Livro atualizarLivro(int id, Livro livro) {

        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não cadastrado.");
        }

        Livro livroParaAtualizar = livroOptional.get();

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setGenero(livro.getGenero());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setTags(livro.getTags());

        livroRepository.save(livroParaAtualizar);

        return livroParaAtualizar;
    }

    public void deletarLivro(int id) {
        buscarLivro(id);
        livroRepository.deleteById(id);
    }

}
