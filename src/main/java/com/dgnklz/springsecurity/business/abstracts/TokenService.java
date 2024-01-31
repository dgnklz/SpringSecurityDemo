package com.dgnklz.springsecurity.business.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims , UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails details);
    boolean validateToken(String token);
    String resolveToken(HttpServletRequest request);
    String getUsername(String token);
}
