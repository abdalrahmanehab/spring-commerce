package com.abdalrahman.springcommerce.shared.utils.enums;

import com.abdalrahman.springcommerce.shared.utils.time.TimeHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SortDirection {
    ASC("asc"),
    DESC("desc");

    final String direction;

    public static SortDirection formType (String sortingDirection){
        return Arrays.stream(SortDirection.values())
                .filter(sortDirection -> sortDirection.hasType(sortingDirection))
                .findFirst()
                .orElseThrow(() -> new SortDirectionException(sortingDirection + " is not a valid direction!"));
    }

    private boolean hasType(String direction) {
        return (this.getDirection().equalsIgnoreCase(direction));
    }


    @Getter
    public static class SortDirectionException extends RuntimeException {
        private final String description;

        public static final int CODE = 9200;
        public static final String MESSAGE = "SortDirectionError";

        private final Timestamp currentTimeStamp = TimeHelper.currentTimeStamp();

        public SortDirectionException (String description) {
            super(description);
            this.description = description;
        }
    }
}
