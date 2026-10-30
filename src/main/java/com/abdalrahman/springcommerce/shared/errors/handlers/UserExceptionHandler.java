package com.abdalrahman.springcommerce.shared.errors.handlers;

import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateEmailException;
import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException.class)
    public GenericResponse<ErrorResponse> handleDuplicateEmailException (DuplicateEmailException e){
        return new GenericResponse<>(
                DuplicateEmailException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(DuplicateEmailException.MESSAGE , e.getDescription())
        );
    }
}
