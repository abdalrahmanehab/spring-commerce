package com.abdalrahman.springcommerce.shared.errors.exceptions;

import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = false)
@Getter
public class DuplicateSkuException extends RuntimeException {
    private final String description;

    private final Timestamp currentTimeStamp = TimeHelper.currentTimeStamp();

    public static int CODE = 2001;
    public static final String MESSAGE = "DuplicateSkuError";

    public DuplicateSkuException(String description) {
        super(description);
        this.description = description;
    }

}
