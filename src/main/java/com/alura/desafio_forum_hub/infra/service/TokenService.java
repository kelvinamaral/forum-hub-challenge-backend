package com.alura.desafio_forum_hub.infra.service;

import com.alura.desafio_forum_hub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("Challenge")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(gerarDataDeExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Falha ao gerar o token JWT", e);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException("Token não pode ser nulo");
        }

        DecodedJWT verifier = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("Challenge")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            System.out.println(e.toString());
            // Exemplo de como melhorar o retorno de erro:
            // throw new RuntimeException("Token inválido ou expirado.", e);
        }

        if (verifier == null || verifier.getSubject() == null) {
            throw new RuntimeException("Verificador inválido ou sem assunto no token");
        }

        return verifier.getSubject();
    }

    private Instant gerarDataDeExpiracao() {
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-06:00"));
    }
}
