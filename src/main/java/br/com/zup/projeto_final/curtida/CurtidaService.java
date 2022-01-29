package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurtidaService {

    @Autowired
    CurtidaRepository curtidaRepository;

    public void salvarCurtida(Curtida curtida){
        curtidaRepository.save(curtida);
    }


}
