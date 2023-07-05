package com.servidorpipoca.pipocaagil.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("977ae362-9ac5-4b2e-8a7d-07f7b7ca42e6")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long validityInMilliseconds;

    public void addTokenToResponse(Authentication authentication, HttpServletResponse response) {
        String token = generateToken(authentication);
        response.addHeader("Authorization", "Bearer " + token);
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}
