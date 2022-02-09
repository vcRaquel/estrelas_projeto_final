package br.com.zup.projeto_final.service;

import br.com.zup.projeto_final.model.Comentario;
import br.com.zup.projeto_final.model.Curtida;
import br.com.zup.projeto_final.repository.ComentarioRepository;
import br.com.zup.projeto_final.model.Review;
import br.com.zup.projeto_final.repository.CurtidaRepository;
import br.com.zup.projeto_final.repository.ReviewRepository;
import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import br.com.zup.projeto_final.customException.RecursoInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurtidaService {

    @Autowired
    CurtidaRepository curtidaRepository;
    @Autowired
    ComentarioRepository comentarioRepository;
    @Autowired
    ReviewRepository reviewRepository;

    public void salvarCurtida(Curtida curtida){
        impedirCurtidaRepetida(curtida);
        atualizarRecurso(curtida.getId_recurso(), String.valueOf(curtida.getTipo()));
        curtidaRepository.save(curtida);
    }

    public void atualizarRecurso(int id, String tipo){
        if (tipo.equals("REVIEW")){
           Review review = reviewRepository.findById(id)
                   .orElseThrow(() -> new RecursoInexistente("Este review não existe"));
               review.setCurtidas(review.getCurtidas() + 1);
           }
        else {
            Comentario comentario = comentarioRepository.findById(id)
                    .orElseThrow(() -> new RecursoInexistente("Este comentário não existe"));
            comentario.setCurtidas(comentario.getCurtidas() + 1);
        }
    }

    public void impedirCurtidaRepetida(Curtida curtida){
        Curtida curtidaEncontrada = curtidaRepository.curtidaRepetida(curtida.getId_usuario(),
                curtida.getId_recurso(), String.valueOf(curtida.getTipo()));
        if (curtidaEncontrada != null){
            throw new CurtidaRepetidaException("Este " + curtida.getTipo() + " já foi curtido por este usuário");
        }
    }


}
