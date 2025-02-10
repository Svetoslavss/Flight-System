package com.academy.flightsystem.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {
        logger.info("generate Token with Username: {}", username);
        var token = buildToken(username);
        logger.info("generate token: {}", token);
        return token;
    }

    private String buildToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey())
                .compact();
    }

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        String username = extractClaims(token, Claims::getSubject);
        logger.info("extract username: {}", username);
        return username;    
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        logger.info("extract claims: {}", claims);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
               .setSigningKey(getSigninKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
    }

    public Long getExpirationTime(){
        return expiration;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return  username.equals(userDetails.getUsername());
    }
}
