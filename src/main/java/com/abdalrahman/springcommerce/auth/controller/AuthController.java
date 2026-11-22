package com.abdalrahman.springcommerce.auth.controller;

import com.abdalrahman.springcommerce.auth.dtos.AuthResponse;
import com.abdalrahman.springcommerce.auth.dtos.LoginRequest;
import com.abdalrahman.springcommerce.auth.service.AuthService;
import com.abdalrahman.springcommerce.user.dtos.RegisterRequest;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;
import com.abdalrahman.springcommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
