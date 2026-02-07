package com.superherogame.superhero_backend.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.superherogame.superhero_backend.entities.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {

    @Value("${security.jwt-key}")
    private String secretKey;

    @Value("${security.jwt.issuer}")
    private String userGenerator;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String generateToken(AppUser user){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return buildToken(user, algorithm);
    }

    public String buildToken(AppUser user, Algorithm algorithm){
        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }
}
