package br.com.zup.projeto_final.seguranca.jwt;

import br.com.zup.projeto_final.Usuario.Usuario;
import br.com.zup.projeto_final.seguranca.jwt.exceptions.AcessoNegadoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class FiltroDeAutenticacaoJWT extends UsernamePasswordAuthenticationFilter {

    private JWTComponent jwtComponent;
    private AuthenticationManager authenticationManager;

    public FiltroDeAutenticacaoJWT(JWTComponent jwtComponent, AuthenticationManager authenticationManager) {
        this.jwtComponent = jwtComponent;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            Usuario login =  objectMapper.readValue(request.getInputStream(), Usuario.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    login.getEmail(), login.getSenha(), new ArrayList<>()
            );
            return  authenticationManager.authenticate(authToken);
        }catch (IOException e){
            throw new AcessoNegadoException();
        }
    }

}
