package com.mariocosta.servicies;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@PropertySource(value = {"classpath:application.yml"})
@Slf4j
public class JwtUtils {

    @Value(
            "${jwt.secret}"
    )
    private String secret;

    private Key key;

    public JwtUtils(@Value("${jwt.secret}") String secret){

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public boolean isExpired (String token){
        log.info("Exp Time -> {}", getClaims(token).getExpiration());
        try {
            return getClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (ExpiredJwtException expired) {
            return true;
        }

    }
}
