package com.abdalrahman.springcommerce.shared.utils.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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


    public static class SortDirectionException extends RuntimeException {
        public SortDirectionException (String message) {
            super(message);
        }
    }
}
