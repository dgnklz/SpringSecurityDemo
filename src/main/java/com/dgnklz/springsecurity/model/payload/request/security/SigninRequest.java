package com.dgnklz.springsecurity.model.payload.request.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
<<<<<<<< HEAD:src/main/java/com/dgnklz/springsecurity/model/payload/request/security/SigninRequest.java
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
========
public class SignInRequest {
>>>>>>>> origin/master:src/main/java/com/dgnklz/springsecurity/business/dto/SignInRequest.java
    @NotBlank(message = "Email is required")
    @Email(regexp = "([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z0-9]+)+)", message = "Email is not valid")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "([A-Za-z0-9-#?!@$%^&*-]*).{5,}$",
            message = "Password must be minimum 5 characters, can contains uppercase, lowercase, number and special character")
    @Size(min = 5, max = 15)
    private String password;
}
