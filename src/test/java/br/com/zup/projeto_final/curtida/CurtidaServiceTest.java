package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.enuns.Tipo;
import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import br.com.zup.projeto_final.model.Comentario;
import br.com.zup.projeto_final.model.Curtida;
import br.com.zup.projeto_final.repository.ComentarioRepository;
import br.com.zup.projeto_final.repository.CurtidaRepository;
import br.com.zup.projeto_final.service.CurtidaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class CurtidaServiceTest {

    @MockBean
    CurtidaRepository curtidaRepository;
    @MockBean
    ComentarioRepository comentarioRepository;
    @Autowired
    CurtidaService curtidaService;

    private Curtida curtida;
    private Comentario comentario;

    @BeforeEach
    public void setup(){
        curtida = new Curtida();
        curtida.setId_curtida(1);
        curtida.setId_recurso(1);
        curtida.setId_usuario("1");
        curtida.setTipo(Tipo.COMENTARIO);

        comentario = new Comentario();
        comentario.setId(1);

    }

    @Test
    public void testarSalvarCurtida(){
        Mockito.when(curtidaRepository.save(Mockito.any(Curtida.class))).thenReturn(curtida);
        Mockito.when(comentarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(comentario));
        curtidaService.salvarCurtida(curtida);
        Mockito.verify(curtidaRepository, Mockito.times(1)).save(curtida);
    }

    @Test
    public void testarImpedirCurtidaRepetidaCaminhoPositivo(){
        Mockito.when(curtidaRepository.curtidaRepetida(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(null);
        curtidaService.impedirCurtidaRepetida(curtida);
    }

    @Test
    public void testarImpedirCurtidaRepetidaCaminhoNegativo(){
        Mockito.when(curtidaRepository.curtidaRepetida(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(curtida);
        CurtidaRepetidaException exception = Assertions.assertThrows(CurtidaRepetidaException.class, () ->{
            curtidaService.salvarCurtida(curtida);
        });
        Assertions.assertEquals("Este COMENTARIO j?? foi curtido por este usu??rio", exception.getMessage());
    }
}
