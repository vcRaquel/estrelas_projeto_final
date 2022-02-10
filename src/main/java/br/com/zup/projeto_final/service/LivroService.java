package br.com.zup.projeto_final.service;

import br.com.zup.projeto_final.components.TratarString;
import br.com.zup.projeto_final.enuns.Genero;
import br.com.zup.projeto_final.enuns.Tags;
import br.com.zup.projeto_final.customException.LivroJaCadastradoException;
import br.com.zup.projeto_final.model.Comentario;
import br.com.zup.projeto_final.customException.AtualizacaoInvalidaException;
import br.com.zup.projeto_final.customException.DelecaoInvalidaException;
import br.com.zup.projeto_final.model.Livro;
import br.com.zup.projeto_final.model.Usuario;
import br.com.zup.projeto_final.repository.LivroRepository;

import br.com.zup.projeto_final.customException.LivroNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    UsuarioLogadoService usuarioLogadoService;
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

    public void adicionarURLImagem(Livro livro){
        if (livro.getImagem() == null){
            livro.setImagem("https://i.pinimg.com/236x/b4/9e/7a/b49e7a7298b855f8bf2cd3f5923ea7ab.jpg");
        }
    }


    public Livro salvarLivro(Livro livro, String idUsuario) {
        String nomeLivro = tratarString.tratarString(livro.getNome());
        String nomeAutor = tratarString.tratarString(livro.getAutor());

        if (livroExistePorNome(nomeLivro, nomeAutor)){
            throw new LivroJaCadastradoException("Livro já cadastrado");
        }

        adicionarURLImagem(livro);
        livro.setNomeTratado(nomeLivro);
        livro.setAutorTratado(nomeAutor);
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




    public Page<Livro> exibirTodosOsLivros(Genero genero, Tags tags, String nome, String autor, Pageable pageable) {
        List<Livro> listaFiltrada = aplicarFiltros(genero, tags, nome, autor, pageable);
        return ordenarLista(listaFiltrada, pageable);

    }

    public List<Livro> aplicarFiltros(Genero genero, Tags tags, String nome, String autor, Pageable pageable){

        List<Livro> livros = new ArrayList<>();

        if (genero == null & tags == null & autor.equals("") & nome.equals("")) {
            for (Livro livro : livroRepository.findAll(pageable)){
                livros.add(livro);
            }
        }else if (genero == null & tags == null){
            for (Livro livro : livroRepository.aplicarFiltroNomeEAutor(nome, autor, pageable)){
                livros.add(livro);
            }
        }else if (genero == null) {
            for (Livro livro : livroRepository.aplicarFiltroTags(String.valueOf(tags), nome, autor, pageable))
            livros.add(livro);
        }else if (tags == null) {
            for (Livro livro : livroRepository.aplicarFiltroGenero(String.valueOf(genero), nome, autor, pageable))
                livros.add(livro);
        } else if (!autor.equals("") | nome.equals("")){
            for (Livro livro : livroRepository.aplicarTodosFiltros(String.valueOf(genero), String.valueOf(tags), nome,
                    autor, pageable))
                livros.add(livro);
        }

        return livros;

    }

    public Page <Livro> ordenarLista(List<Livro> listaFiltrada, Pageable pageable){

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
        return new PageImpl<>(livrosOrdenados, pageable, livrosOrdenados.size());
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

        Usuario usuarioLogado = usuarioService.buscarUsuario(usuarioLogadoService.pegarId());
        Optional<Livro> livroOptional = livroRepository.findById(id);
        if (livroOptional.isEmpty()) {
            throw new LivroNaoEncontradoException("Livro não cadastrado.");
        }

        if (livroOptional.get().getQuemCadastrou() != usuarioLogado){
            throw new AtualizacaoInvalidaException("Você só pode atualizar os livros que você cadastrou");
        }

        Livro livroParaAtualizar = livroOptional.get();

        livroParaAtualizar.setNome(livro.getNome());
        livroParaAtualizar.setGenero(livro.getGenero());
        livroParaAtualizar.setLido(livro.isLido());
        livroParaAtualizar.setImagem(livro.getImagem());
        livroParaAtualizar.setTags(livro.getTags());

        livroRepository.save(livroParaAtualizar);

        return livroParaAtualizar;
    }

    public void deletarLivro(int id) {
        Usuario usuarioLogado = usuarioService.buscarUsuario(usuarioLogadoService.pegarId());
        Livro livro = buscarLivro(id);
        if (livro.getQuemCadastrou() != usuarioLogado){
            throw new DelecaoInvalidaException("Você só pode deletar seus próprios livros");
        }
        livroRepository.deleteById(id);
    }

}
