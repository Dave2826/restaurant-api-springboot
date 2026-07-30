package com.david.restaurantapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utilidad para generar y validar tokens JWT.
 *
 * @author David Morales Guerrero
 */
@Component
// JWT / HS256
public class JwtUtil {

    // SecretKey
    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    /**
     * Genera un token JWT para el usuario indicado.
     *
     * @param username nombre de usuario
     * @return token JWT generado
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                // HS256
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario del token.
     *
     * @param token token JWT
     * @return nombre de usuario contenido en el token
     */
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Valida que el token sea valido para el usuario indicado.
     *
     * @param token    token JWT
     * @param username nombre de usuario a validar
     * @return true si el token es valido, false en caso contrario
     */
    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
