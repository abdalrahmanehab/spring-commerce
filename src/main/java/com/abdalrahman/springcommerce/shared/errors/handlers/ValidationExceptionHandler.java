package com.abdalrahman.springcommerce.shared.errors.handlers;

import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import com.abdalrahman.springcommerce.shared.utils.enums.SortDirection;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.abdalrahman.springcommerce.shared.utils.time.TimeHelper.currentTimeStamp;

import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse<List<ErrorResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorResponse> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();

        return new GenericResponse<>(1001, currentTimeStamp(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public GenericResponse<List<ErrorResponse>> handleConstraintViolationException(ConstraintViolationException e) {
        List<ErrorResponse> errors = e.getConstraintViolations()
                .stream()
                .map(v -> new ErrorResponse(v.getPropertyPath().toString(), v.getMessage()))
                .toList();

        return new GenericResponse<>(1002, currentTimeStamp(), errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SortDirection.SortDirectionException.class)
    public GenericResponse<ErrorResponse> handleSortDirectionException(SortDirection.SortDirectionException e) {
        return new GenericResponse<>(
                SortDirection.SortDirectionException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(SortDirection.SortDirectionException.MESSAGE, e.getDescription())
        );
    }
}
