package com.abdalrahman.springcommerce.user.service;

import com.abdalrahman.springcommerce.user.dtos.RegisterRequest;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;

import java.util.Collection;

public interface UserService {

    UserResponse register(RegisterRequest request);

    UserResponse getById(Long id);

    UserResponse getByEmail(String email);

    Collection<UserResponse> getAll();

    void delete(Long id);

}
