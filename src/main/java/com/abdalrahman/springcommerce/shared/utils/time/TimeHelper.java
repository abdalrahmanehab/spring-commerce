package com.abdalrahman.springcommerce.shared.utils.time;

import java.sql.Timestamp;
import java.time.Instant;

public final class TimeHelper {

    private TimeHelper() {
        throw new AssertionError("Utility class , Can not be instantiated!");
    }

    public static Timestamp currentTimeStamp() {
        return Timestamp.from(Instant.now());
    }
}
