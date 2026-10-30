package com.abdalrahman.springcommerce.shared.utils.mapper;

import com.abdalrahman.springcommerce.user.User;
import com.abdalrahman.springcommerce.user.dtos.RegisterRequest;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;

public class UserMapper {
    private UserMapper() {
        throw new AssertionError("Utility class , Can not be instantiated!");
    }

    public static UserResponse toUserResponse(final User user){
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }

    public static User toUser (final RegisterRequest request){
        return User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
    }
}
