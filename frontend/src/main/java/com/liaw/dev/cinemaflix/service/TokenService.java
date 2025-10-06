package com.liaw.dev.cinemaflix.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liaw.dev.cinemaflix.dto.JwtUserData;
import com.liaw.dev.cinemaflix.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenService {

    public String generateToken(User user){
        Algorithm algorithm = Algorithm.HMAC256("hehe-boy");

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("user_id", user.getId())
                .withClaim("user_username", user.getUsername())
                .withIssuer("API_CINEFLIX")
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .sign(algorithm);

    }

    public Optional<JwtUserData> verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256("hehe-boy");

        DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

        return Optional.of(
                JwtUserData.builder()
                        .id(jwt.getClaim("user_id").asLong())
                        .username(jwt.getClaim("user_username").asString())
                        .email(jwt.getSubject())
                        .build()
        );
    }

}
