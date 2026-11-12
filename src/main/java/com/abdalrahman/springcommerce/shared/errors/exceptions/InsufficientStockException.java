package com.abdalrahman.springcommerce.shared.errors.exceptions;

import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = false)
@Getter
public class InsufficientStockException extends RuntimeException {
    private final String description;

    private final Timestamp currentTimeStamp = TimeHelper.currentTimeStamp();

    public static final int CODE = 2003;
    public static final String MESSAGE = "InsufficientStockException";

    public InsufficientStockException(String description) {
        super(description);
        this.description = description;
    }
}
