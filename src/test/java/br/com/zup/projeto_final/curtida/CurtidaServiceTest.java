package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.Enun.Tipo;
import br.com.zup.projeto_final.customException.CurtidaRepetidaException;
import br.com.zup.projeto_final.customException.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CurtidaServiceTest {

    @MockBean
    CurtidaRepository curtidaRepository;
    @Autowired
    CurtidaService curtidaService;

    private Curtida curtida;

    @BeforeEach
    public void setup(){
        curtida = new Curtida();
        curtida.setId_curtida(1);
        curtida.setId_recurso(1);
        curtida.setId_usuario("1");
        curtida.setTipo(Tipo.COMENTARIO);
    }

    @Test
    public void testarSalvarCurtida(){
        Mockito.when(curtidaRepository.save(Mockito.any(Curtida.class))).thenReturn(curtida);
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
        Assertions.assertEquals("Este COMENTARIO já foi curtido por este usuário", exception.getMessage());
    }
}
