package com.dgnklz.springsecurity.controller;

import com.dgnklz.springsecurity.business.abstracts.TokenService;
import com.dgnklz.springsecurity.core.Message.ResponseMessage;
import com.dgnklz.springsecurity.model.UserDetailsImpl;
import com.dgnklz.springsecurity.model.entity.User;
import com.dgnklz.springsecurity.model.entity.UserRole;
import com.dgnklz.springsecurity.model.payload.request.security.SigninRequest;
import com.dgnklz.springsecurity.model.payload.request.security.SignupRequest;
import com.dgnklz.springsecurity.model.payload.response.security.SigninResponse;
import com.dgnklz.springsecurity.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;

    UserRepository repository;

    PasswordEncoder encoder;

    TokenService tokenService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        if (repository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(new ResponseMessage("Email is already in use!"));
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        if(request.getRole().equals("admin")) {
            user.setRole(UserRole.ADMIN);
        }else if (request.getRole().equals("mod")) {
            user.setRole(UserRole.MODERATOR);
        }else {
            throw new RuntimeException("Role is not found");
        }

        repository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered successfully !"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateAuthToken(authentication);

        SigninResponse response = new SigninResponse();
        response.setToken(token);

        return ResponseEntity.ok(new SigninResponse(token));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }
}
