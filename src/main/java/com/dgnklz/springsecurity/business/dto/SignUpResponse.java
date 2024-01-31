package com.dgnklz.springsecurity.business.dto;

import com.dgnklz.springsecurity.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpResponse {
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
}
