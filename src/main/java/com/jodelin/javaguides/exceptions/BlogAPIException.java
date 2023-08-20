package com.jodelin.javaguides.exceptions;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

    private final String message;

    public BlogAPIException(HttpStatus badRequest, String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
