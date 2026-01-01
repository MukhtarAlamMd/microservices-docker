package com.ecomics.user_service.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET =
            "my-super-secret-jwt-key-which-is-at-least-32-characters-long";

    private static final long ACCESS_TOKEN_EXPIRY = 1000 * 60 * 15;   // 15 min
    private static final long REFRESH_TOKEN_EXPIRY = 1000 * 60 * 60 * 24 * 7; // 7 days

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // 🔐 ACCESS TOKEN
    public String generateAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY)
                )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔄 REFRESH TOKEN
    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY)
                )
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Validate & extract username
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
