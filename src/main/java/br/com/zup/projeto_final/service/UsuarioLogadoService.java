package br.com.zup.projeto_final.service;

import br.com.zup.projeto_final.security.UsuarioLogado;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {

    public String pegarId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UsuarioLogado usuarioLogado = (UsuarioLogado) principal;
        return usuarioLogado.getId();
    }

}
