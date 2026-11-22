package com.abdalrahman.springcommerce.auth.service;

import com.abdalrahman.springcommerce.auth.dtos.AuthResponse;
import com.abdalrahman.springcommerce.auth.dtos.LoginRequest;
import com.abdalrahman.springcommerce.security.JwtService;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;
import com.abdalrahman.springcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.getEmail(), request.getPassword()
                ));

        UserResponse user = userService.getByEmail(authentication.getName());
        String role = user.role().name();

        log.info("User logged in [id={}]", user.id());
        return new AuthResponse(jwtService.generateToken(user.email(), role), user.email(), role);

    }
}
