package com.project.todo.jwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    // get key
    public Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // generate JwT
    public String generateToken(UUID userId, String email) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .claim("email", email)
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(getKey())
                .compact();
    }

    // extract subject/email from JwT
    public String extractIdentityFromToken(String token) {
        Key key = getKey();

        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
