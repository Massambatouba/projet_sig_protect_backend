package com.makarimal.aisprotect_back.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ Clé JWT plus longue et sécurisée (512 bits minimum pour HS512)
    @Value("${jwt.secret:monSecretTresTresLongPourJWT2024!@#$%^&*()ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // 24 heures en millisecondes
    private Long jwtExpirationMs;

    private SecretKey getSigningKey() {
        // ✅ Génération d'une clé sécurisée pour HS512
        if (jwtSecret.length() < 64) {
            // Si la clé est trop courte, on en génère une automatiquement
            return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        }
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}