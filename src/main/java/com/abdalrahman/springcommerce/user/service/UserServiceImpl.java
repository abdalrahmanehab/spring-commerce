package com.abdalrahman.springcommerce.user.service;

import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateEmailException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.utils.mapper.UserMapper;
import com.abdalrahman.springcommerce.user.Role;
import com.abdalrahman.springcommerce.user.User;
import com.abdalrahman.springcommerce.user.dtos.RegisterRequest;
import com.abdalrahman.springcommerce.user.dtos.UserResponse;
import com.abdalrahman.springcommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) throws DuplicateEmailException {

        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Duplicate Email detected [email={}]", request.getEmail());
            throw new DuplicateEmailException("User Already registered!");
        }

        User user = UserMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);

        User savedUser = userRepository.save(user);
        log.info("User registered successfully [id={}, email={}]", savedUser.getId(), savedUser.getEmail());

        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getById(final Long id) throws ResourceNotFoundException {
        return userRepository.findById(id)
                .map(UserMapper::toUserResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public UserResponse getByEmail(String email) throws ResourceNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserMapper::toUserResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with Email: " + email));
    }

    @Override
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserResponse)
                .toList();

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        userRepository.deleteById(id);
        log.info("User deleted [id={}]", id);
    }
}
