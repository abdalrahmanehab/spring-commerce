package com.abdalrahman.springcommerce.user.controller;

import com.abdalrahman.springcommerce.security.CurrentUser;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;
import com.abdalrahman.springcommerce.user.service.UserService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final CurrentUser currentUser;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse me() {
        return userService.getById(currentUser.id());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getById(@PathVariable @Positive Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long id) {
        userService.delete(id);
    }
}