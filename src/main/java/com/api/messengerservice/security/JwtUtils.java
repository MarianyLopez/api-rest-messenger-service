package com.api.messengerservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;

public class JwtUtils {

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    private  final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;

    public static String createToken(String email){
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String email = claims.getSubject();

            return new UsernamePasswordAuthenticationToken(email,null, Collections.emptyList());
        }catch (JwtException e){
            return null;
        }
    }


}
