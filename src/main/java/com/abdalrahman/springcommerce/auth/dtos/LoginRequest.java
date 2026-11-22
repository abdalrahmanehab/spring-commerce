package com.abdalrahman.springcommerce.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "{auth.email.required}")
    @Email(message = "{auth.email.invalid}")
    private String email;

    @NotBlank(message = "{auth.password.required}")
    private String password;
}