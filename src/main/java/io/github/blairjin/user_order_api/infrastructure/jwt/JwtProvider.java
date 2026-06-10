package io.github.blairjin.user_order_api.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final SecretKey key;

    public JwtProvider(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(JwtUserInfo userInfo) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.accessTokenExpiration().toMillis());

        String token = Jwts.builder()
                .signWith(key)
                .issuedAt(now)
                .expiration(expiration)
                .subject(String.valueOf(userInfo.userId()))
                .claim("loginId", userInfo.loginId())
                .claim("role", userInfo.role())
                .compact();

        return token;
    }

    public String createRefreshToken(Long userId){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.refreshTokenExpiration().toMillis());

        String token = Jwts.builder()
                .signWith(key)
                .issuedAt(now)
                .expiration(expiration)
                .subject(String.valueOf(userId))
                .compact();

        return token;
    }

    public boolean validateToken(String token){
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

    public Long getUserId(String token){
        String userId = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return Long.valueOf(userId);
    }

    public JwtUserInfo getUserInfo(String accessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        return JwtUserInfo.from(claims);
    }
}