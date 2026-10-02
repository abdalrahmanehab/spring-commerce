package com.abdalrahman.springcommerce.shared.errors.handlers;

import com.abdalrahman.springcommerce.shared.errors.exceptions.ResourceNotFoundException;
import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import org.springframework.http.HttpStatus;
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

}
