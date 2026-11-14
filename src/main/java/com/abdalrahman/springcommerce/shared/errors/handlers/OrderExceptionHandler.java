package com.abdalrahman.springcommerce.shared.errors.handlers;

import com.abdalrahman.springcommerce.shared.errors.exceptions.EmptyCartException;
import com.abdalrahman.springcommerce.shared.errors.exceptions.InvalidOrderStateException;
import com.abdalrahman.springcommerce.shared.errors.models.ErrorResponse;
import com.abdalrahman.springcommerce.shared.errors.models.GenericResponse;
import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {


    private static final int OPTIMISTIC_LOCK_CODE = 4003;
    private static final String OPTIMISTIC_LOCK_MESSAGE = "OptimisticLockError";
    private static final String OPTIMISTIC_LOCK_DESCRIPTION =
            "Product stock was updated by another transaction, please try again";

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvalidOrderStateException.class)
    public GenericResponse<ErrorResponse> handleInvalidOrderStateException(InvalidOrderStateException e) {
        return new GenericResponse<>(
                InvalidOrderStateException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(InvalidOrderStateException.MESSAGE, e.getDescription())
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyCartException.class)
    public GenericResponse<ErrorResponse> handleEmptyCartException(EmptyCartException e) {
        return new GenericResponse<>(
                EmptyCartException.CODE,
                e.getCurrentTimeStamp(),
                new ErrorResponse(EmptyCartException.MESSAGE, e.getDescription())
        );
    }

    /**
     * Handles concurrent inventory modification conflicts detected by JPA {@code @Version} optimistic locking.
     *
     * @param e the thrown {@link ObjectOptimisticLockingFailureException}
     * @return a {@link GenericResponse} wrapping the conflict details with HTTP 409 Conflict
     */

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public GenericResponse<ErrorResponse> handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException e) {
        return new GenericResponse<>(
                OPTIMISTIC_LOCK_CODE,
                TimeHelper.currentTimeStamp(),
                new ErrorResponse(OPTIMISTIC_LOCK_MESSAGE, OPTIMISTIC_LOCK_DESCRIPTION)
        );
    }

}
