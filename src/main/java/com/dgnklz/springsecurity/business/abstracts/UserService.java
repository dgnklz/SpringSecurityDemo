package com.dgnklz.springsecurity.business.abstracts;

import com.dgnklz.springsecurity.business.dto.*;
import com.dgnklz.springsecurity.entity.User;

public interface UserService {
    SignUpResponse signup(SignUpRequest request);
    SignInResponse signin(SignInRequest request);
    SignInResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
