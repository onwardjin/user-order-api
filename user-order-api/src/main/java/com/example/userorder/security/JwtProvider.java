package com.example.userorder.security;

import com.example.userorder.domain.user.vo.LoginId;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtProvider {
    private static final String SECRET_KEY = "afdd-wesd-tsdv-qpwx-zpqw-asld-afdd-wesd-tsdv-qpwx-zpqw-asld";
    private static final Long EXPIRATION = 1000L * 60 * 60;

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String createToken(LoginId loginId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .signWith(key)
                .issuedAt(now)
                .expiration(expiry)
                .subject(loginId.value())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public LoginId getLoginId(String token) {
        String loginId = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return LoginId.of(loginId);
    }
}