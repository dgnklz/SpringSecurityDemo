package com.dgnklz.springsecurity.business.abstracts;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {
    String generateUserToken(UserDetails userDetails);
    String generateAuthToken(Authentication authentication);
    String getUsername(String token);
    boolean validateToken(String token);

}
