package com.dgnklz.springsecurity.business.concretes;

import com.dgnklz.springsecurity.business.abstracts.TokenService;
import com.dgnklz.springsecurity.business.abstracts.UserService;
import com.dgnklz.springsecurity.business.dto.*;
import com.dgnklz.springsecurity.entity.User;
import com.dgnklz.springsecurity.entity.UserRole;
import com.dgnklz.springsecurity.core.exception.BusinessException;
import com.dgnklz.springsecurity.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@AllArgsConstructor
@Service
public class UserManager implements UserService, UserDetailsService {
    private UserRepository repository;
    private PasswordEncoder encoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Override
    public SignUpResponse signup(SignUpRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setRole(UserRole.USER);
        user.setPassword(encoder.encode(request.getPassword()));
        repository.save(user);

        SignUpResponse response = new SignUpResponse();
        response.setEmail(user.getEmail());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setRole(UserRole.USER);

        return response;
    }

    @Override
    public SignInResponse signin(SignInRequest request) {
        checkIfUserNotExistByEmail(request.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),
                request.getPassword()));
        var user = repository.findByEmail(request.getEmail());
        var token = tokenService.generateToken(user);
        var refreshToken = tokenService.generateRefreshToken(new HashMap<>(), user);

        SignInResponse response = new SignInResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);

        return response;
    }

    public SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = tokenService.getUsername(refreshTokenRequest.getToken());
        checkIfUserNotExistByEmail(userEmail);

        User user = repository.findByEmail(userEmail);
        if (tokenService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var token = tokenService.generateToken(user);

            SignInResponse response = new SignInResponse();
            response.setToken(token);
            response.setRefreshToken(refreshTokenRequest.getToken());

            return response;
        }
        return null;
    }

    ///For UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkIfUserNotExistByEmail(username);
        return repository.findByEmail(username);
    }

    /// Domain Rules \\\
    private void checkIfUserNotExistByEmail(String email) {
        if (!repository.existsUserByEmail(email)) {
            throw new BusinessException("Email not found");
        }
    }

    private void checkIfUserExistByEmail(String email) {
        if (!repository.existsUserByEmail(email)) {
            throw new BusinessException("Email exist");
        }
    }


}
