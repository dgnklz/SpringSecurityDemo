package com.dgnklz.springsecurity.business.concretes;

import com.dgnklz.springsecurity.business.abstracts.TokenService;
import com.dgnklz.springsecurity.core.exception.BusinessException;
import com.dgnklz.springsecurity.core.exception.TokenException;
import com.dgnklz.springsecurity.model.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenManager implements TokenService {
    @Value("${auth-service.secret.key}")
    private String secretKey;

    @Value("${auth-service.expire.time}")
    private int expirationTime;

    @Override
    public String generateUserToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateAuthToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new TokenException("Invalid Token");
        } catch (ExpiredJwtException e) {
            throw new TokenException("Token is expired");
        } catch (UnsupportedJwtException e) {
            throw new TokenException("Token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new TokenException("Token claims string is empty");
        }
    }

    private Key key() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
