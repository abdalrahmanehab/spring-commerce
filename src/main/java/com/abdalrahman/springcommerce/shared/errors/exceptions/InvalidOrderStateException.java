package com.abdalrahman.springcommerce.shared.errors.exceptions;

import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;


@EqualsAndHashCode(callSuper = false)
@Getter
public class InvalidOrderStateException extends RuntimeException{
    private final String description;

    private final Timestamp currentTimeStamp = TimeHelper.currentTimeStamp();

    public static final int CODE = 3001;
    public static final String MESSAGE = "InvalidOrderStateError";

    public InvalidOrderStateException(String description) {
        super(description);
        this.description = description;
    }
}
