package com.abdalrahman.springcommerce.errors.models;

import java.sql.Timestamp;

public record GenericResponse<T>(int code , Timestamp timestamp ,T body) {
}
