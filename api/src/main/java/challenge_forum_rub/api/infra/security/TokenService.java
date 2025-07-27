package challenge_forum_rub.api.infra.security;

import challenge_forum_rub.api.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);
    private static final String ISSUER = "API forum.rub";



    public String gerarToken(Usuario usuario){

        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }

    }

    public String getSubject(String tokenJWT){
        try {

            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            //exception.printStackTrace();
            throw new RuntimeException("Token JWT inválido ou expirado" + tokenJWT, exception);
        }
    }



    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(48).toInstant(ZoneOffset.of("-03:00"));//criar a data de expiração conforme o horário do Brasil

    }

}

