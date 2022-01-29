package br.com.zup.projeto_final.curtida;

import br.com.zup.projeto_final.Enun.Tipo;
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
        curtida.setId_curtida(1L);
        curtida.setId_recurso(1L);
        curtida.setId_usuario("1");
        curtida.setTipo(Tipo.COMENTARIO);
    }

    @Test
    public void testarSalvarCurtida(){
        Mockito.when(curtidaRepository.save(Mockito.any(Curtida.class))).thenReturn(curtida);
        curtidaService.salvarCurtida(curtida);
        Mockito.verify(curtidaRepository, Mockito.times(1)).save(curtida);
    }
}
