package br.com.zup.projeto_final.seguranca.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JWTComponent {
    private final String segredo = "teste";
    private final Long milissegundo = 600000L;

    public String gerarToken(String username, String id) {
        Date vencimento = new Date(System.currentTimeMillis() + milissegundo);

        String token = Jwts.builder().setSubject(username)
                .claim("idUsuario", id).setExpiration(vencimento).claim("aleatorio", "xablau")
                .signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();

        return token;
    }

    public Claims pegarClaims(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(segredo.getBytes()).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            throw new TokenInvalidoException();
        }
    }

}