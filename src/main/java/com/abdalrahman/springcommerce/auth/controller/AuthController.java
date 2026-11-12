package com.abdalrahman.springcommerce.auth.controller;

import com.abdalrahman.springcommerce.user.dtos.RegisterRequest;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;
import com.abdalrahman.springcommerce.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request){
        return userService.register(request);
    }

}
