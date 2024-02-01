package com.dgnklz.springsecurity.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignupResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String role;
}
