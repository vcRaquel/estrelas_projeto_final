package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurtidaService {

    @Autowired
    CurtidaRepository curtidaRepository;

    public void salvarCurtida(Curtida curtida){
        impedirCurtidaRepetida(curtida);
        curtidaRepository.save(curtida);
    }

    public void impedirCurtidaRepetida(Curtida curtida){
        Curtida curtidaEncontrada = curtidaRepository.curtidaRepetida(curtida.getId_usuario(),
                curtida.getId_recurso(), String.valueOf(curtida.getTipo()));
        if (curtidaEncontrada != null){
            throw new CurtidaRepetidaException("Este " + curtida.getTipo() + " já foi curtido por este usuário");
        }
    }


}
