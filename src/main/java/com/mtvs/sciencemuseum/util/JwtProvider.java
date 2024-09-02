package com.mtvs.sciencemuseum.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey SECRET_KEY;

    @Value("${jwt.expiration}")
    private Long TOKEN_EXPIRATION;



    public JwtProvider(@Value("${jwt.secret}") String secret) {
        this.SECRET_KEY = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts
                        .SIG
                        .HS256
                        .key()
                        .build()
                        .getAlgorithm()
        );
    }

    public String createAccessToken(String nickname, String role) {

        return Jwts.builder()
                .header()
                .keyId("access")
                .and()
                .subject(nickname)// 닉네임
                .claim("role", role)
                .issuer("CourseMaker")
                .issuedAt(new Date(System.currentTimeMillis()) )
                .expiration(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION) )
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean isExpired(String token){
        try{
            Jwts
                    .parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    public String getNickname(String token) {
        return Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getRoles(String token) {
        return (String)Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
    }

}
