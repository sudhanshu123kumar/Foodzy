package com.EcommerceWeb.Foodzy.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenHelper {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    private Key getSignKey(){

        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(String email){

       return Jwts.builder()
               .setSubject(email)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
               .signWith(getSignKey(), SignatureAlgorithm.HS256)
               .compact();
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(getSignKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    public String extractUsername(String token){

        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token){

        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String email) {

        String username = extractUsername(token);

        return username.equals(email)
                && !isTokenExpired(token);
    }
}
