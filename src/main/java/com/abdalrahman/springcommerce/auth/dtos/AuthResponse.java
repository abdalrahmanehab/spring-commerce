package com.abdalrahman.springcommerce.auth.dtos;

public record AuthResponse(String token, String email, String role) {
}
