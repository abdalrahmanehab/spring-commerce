package com.abdalrahman.springcommerce.shared.errors.handlers;

import com.abdalrahman.springcommerce.shared.errors.exceptions.InsufficientStockException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public GenericResponse<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new GenericResponse<>(
                ResourceNotFoundException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(ResourceNotFoundException.MESSAGE, e.getDescription())
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InsufficientStockException.class)
    public GenericResponse<ErrorResponse> handleInsufficientStockException(InsufficientStockException e) {
        return new GenericResponse<>(
                InsufficientStockException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(InsufficientStockException.MESSAGE, e.getDescription())
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public GenericResponse<ErrorResponse> handleAccessDeniedException(AccessDeniedException e){
        return new GenericResponse<>(
                4030,
                TimeHelper.currentTimeStamp(),
                new ErrorResponse("AccessDeniedExceptionError", "You do not have permission to access this resource")
        );
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public GenericResponse<ErrorResponse> handleBadCredentials(BadCredentialsException e) {
        return new GenericResponse<>(
                4011,
                TimeHelper.currentTimeStamp(),
                new ErrorResponse("InvalidCredentialsError", "Invalid email or password"));
    }

}
