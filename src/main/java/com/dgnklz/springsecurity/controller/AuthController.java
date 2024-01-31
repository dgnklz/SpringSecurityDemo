package com.dgnklz.springsecurity.controller;

import com.dgnklz.springsecurity.business.abstracts.UserService;
import com.dgnklz.springsecurity.business.dto.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/signup")
    public SignUpResponse signup(@Valid @RequestBody SignUpRequest request) {
        return userService.signup(request);
    }

    @PostMapping("/signin")
    public SignInResponse signin(@Valid @RequestBody SignInRequest request) {
        return userService.signin(request);
    }

    @PostMapping("/refresh/{refreshTokenRequest}")
    public SignInResponse refreshToken(@PathVariable RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest);
    }
}
