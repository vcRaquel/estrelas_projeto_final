package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.Textos.comentario.Comentario;
import br.com.zup.projeto_final.Textos.comentario.ComentarioRepository;
import br.com.zup.projeto_final.Textos.review.Review;
import br.com.zup.projeto_final.Textos.review.ReviewRepository;
import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import br.com.zup.projeto_final.customException.RecursoInexistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
