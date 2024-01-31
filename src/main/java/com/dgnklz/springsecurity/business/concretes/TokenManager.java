package com.dgnklz.springsecurity.business.concretes;

import com.dgnklz.springsecurity.business.abstracts.TokenService;
import com.dgnklz.springsecurity.core.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class TokenManager implements TokenService {
    @Value("${auth-service.secret.key}")
    String secretKey;

    @Value("${auth-service.expire.time}")
    Long expireDate;

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getSigninKey()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new TokenException(token, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new TokenException(token, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new TokenException(token, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new TokenException(token, "JWT claims string is empty");
        } catch (SignatureException e) {
            throw new TokenException(token, "there is an error with the signature of you token ");
        }
    }

    public boolean isTokenValid(String token, UserDetails details) {
        String username = getUsername(token);
            return (username.equals(details.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public String getUsername(String token) {
        // Extract the username from the JWT token
        return Jwts.parser()
                .setSigningKey(getSigninKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
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
