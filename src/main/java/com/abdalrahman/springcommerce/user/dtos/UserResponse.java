package com.abdalrahman.springcommerce.user.dtos;

import com.abdalrahman.springcommerce.user.Role;

public record UserResponse(
        Long id
        , String email
        , String firstName
        , String lastName
        , Role role) {
}