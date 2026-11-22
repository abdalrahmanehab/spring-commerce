package com.abdalrahman.springcommerce.security;

import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RestSecurityErrorHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    private static final int UNAUTHORIZED_CODE = 4010;
    private static final int FORBIDDEN_CODE = 4030;

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        write(response, HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_CODE,
                "UnauthorizedError", "Authentication is required");
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException ex) throws IOException {
        write(response, HttpServletResponse.SC_FORBIDDEN, FORBIDDEN_CODE,
                "ForbiddenError", "You do not have permission to access this resource");
    }

    private void write(HttpServletResponse response, int status, int code,
                       String message, String description) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(),
                new GenericResponse<>(code, TimeHelper.currentTimeStamp(),
                        new ErrorResponse(message, description)));
    }

}
