package com.personalresume.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long expirationMinutes;

    public JwtTokenProvider(@Value("${app.jwt.secret}") String secret,
                            @Value("${app.jwt.expiration-minutes}") long expirationMinutes) {
        this.key = Keys.hmacShaKeyFor(resolveSecret(secret));
        this.expirationMinutes = expirationMinutes;
    }

    public String generateToken(Long userId, String username) {
        Instant now = Instant.now();
        Instant expiry = now.plus(expirationMinutes, ChronoUnit.MINUTES);

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userId);
        claims.put("username", username);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .subject(username)
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validate(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private byte[] resolveSecret(String secret) {
        String trimmed = secret == null ? "" : secret.trim();

        if (trimmed.length() >= 32) {
            return trimmed.getBytes(StandardCharsets.UTF_8);
        }

        StringBuilder builder = new StringBuilder(trimmed);
        while (builder.length() < 32) {
            builder.append("_secure_key");
        }
        return builder.substring(0, 32).getBytes(StandardCharsets.UTF_8);
    }
}
