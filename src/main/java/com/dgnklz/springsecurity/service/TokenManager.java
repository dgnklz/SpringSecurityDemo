package com.dgnklz.springsecurity.service;

import com.dgnklz.springsecurity.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class TokenManager {
    @Value("${auth-service.secret.key}")
    String secretKey;

    @Value("${auth-service.expire.time}")
    Long expireDate;

    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigninKey()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new TokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new TokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new TokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new TokenException("JWT claims string is empty");
        } catch (SignatureException e) {
            throw new TokenException("there is an error with the signature of you token ");
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getUsername(String token) {
        // Extract the username from the JWT token
        return Jwts.parser()
                .setSigningKey(getSigninKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSigninKey() {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return key;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }
}
