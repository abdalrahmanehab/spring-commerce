package com.abdalrahman.springcommerce.auth.service;

import com.abdalrahman.springcommerce.auth.dtos.AuthResponse;
import com.abdalrahman.springcommerce.auth.dtos.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
