package com.abdalrahman.springcommerce.security;

import com.abdalrahman.springcommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUser {
    private final UserService userService;

    public void checkOwnerAndAdmin(Long ownerId) {

        Authentication authentication = authentication();

        if (!isAdmin(authentication) && !id().equals(ownerId)) {
            throw new AccessDeniedException("Access denied");
        }
    }

    public Long id() {
        String email = authentication().getName();
        return userService.getByEmail(email).id();
    }

    private Authentication authentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth instanceof AnonymousAuthenticationToken || !auth.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Not an authenticated user");
        }

        return auth;
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
