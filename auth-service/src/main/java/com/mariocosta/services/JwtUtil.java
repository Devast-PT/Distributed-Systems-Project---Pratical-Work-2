package com.mariocosta.services;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

    @Value(
            "${jwt.secret}"
    )
    public String secret;

    @Value(
            "${jwt.expiration}"
    )
    public String expiration;

    private Key key;

    public JwtUtil(@Value("${jwt.secret}") String secret){
        if (Objects.nonNull(secret))
            this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
    }


    public Date getExpirationDate (String token){
        return getClaims(token).getExpiration();
    }

    public boolean isExpired (String token){
        return getExpirationDate(token).before(new Date());
    }



    public String generate(String userId, String role, String tokenType){
        Map<String, String> claims = Map.of(
                "id", userId, "role", role
        );

        long expMillis = "ACCESS".equalsIgnoreCase(tokenType) ? (Long.parseLong(expiration) * 60) : Long.parseLong(expiration) * 5;

        final Date now = new Date(System.currentTimeMillis());
        final Date exp = new Date(System.currentTimeMillis() + expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.get("id"))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }


    public String buildToken(Claims claims, String tokenType){
        long expMillis = "ACCESS".equalsIgnoreCase(tokenType) ? Long.parseLong(expiration) * 2 : Long.parseLong(expiration) * 5;

        final Date now = new Date(System.currentTimeMillis());
        final Date exp = new Date(System.currentTimeMillis() + expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject((String) claims.get("id"))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public Claims getClaimsRefresh(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromRefresh (String token){
        return getClaimsRefresh(token).getExpiration();
    }

    public boolean isExpiredRefresh (String token){
        return getExpirationDateFromRefresh(token).before(new Date());
    }



}
