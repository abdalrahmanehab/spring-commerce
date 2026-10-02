package com.abdalrahman.springcommerce.shared.errors.exceptions;

import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = false)
@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String description;

    private final Timestamp currentTimeStamp = TimeHelper.currentTimeStamp();

    public static int CODE = 1004;
    public static final String MESSAGE = "ResourceNotFoundError";

    public ResourceNotFoundException(String description) {
        super(description);
        this.description = description;
    }

}
