package com.edteam.api.processor.enums;

import org.springframework.http.HttpStatus;

public enum APIError {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "The are attributes with wrong values"),
    BAD_FORMAT(HttpStatus.BAD_REQUEST, "The message not have a correct form"),
    FILES_NOT_FOUND(HttpStatus.NOT_FOUND, "Files not found");

    private final HttpStatus httpStatus;
    private final String message;

    APIError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
