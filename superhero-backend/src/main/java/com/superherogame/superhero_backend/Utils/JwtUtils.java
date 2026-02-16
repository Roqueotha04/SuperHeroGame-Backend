package com.superherogame.superhero_backend.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
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

    public String generateEmailConfirmationToken(AppUser user){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return buildEmailConfirmationToken(user, algorithm);
    }

    public String buildEmailConfirmationToken(AppUser user, Algorithm algorithm){
        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(user.getId().toString())
                .withClaim("type", "email_confirmation")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token){
        try{
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(userGenerator)
                .build();
        return verifier.verify(token);

        }catch (
        TokenExpiredException exception) {
            throw new JWTVerificationException("Token has expired");
        } catch (
        SignatureVerificationException exception) {
            throw new JWTVerificationException("Token signature is invalid (Tampering detected)");
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token is invalid: " + exception.getMessage());
        }
    }

    public String extractSubject(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public String extractClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName).asString();
    }
}
