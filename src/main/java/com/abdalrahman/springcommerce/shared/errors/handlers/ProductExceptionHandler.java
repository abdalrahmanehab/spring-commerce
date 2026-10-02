package com.abdalrahman.springcommerce.shared.errors.handlers;

import com.abdalrahman.springcommerce.shared.errors.exceptions.DuplicateSkuException;
import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateSkuException.class)
    public GenericResponse<ErrorResponse> handleDuplicateSkuException(DuplicateSkuException e) {
        return new GenericResponse<>(
                DuplicateSkuException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(DuplicateSkuException.MESSAGE, e.getDescription())
        );
    }
}
