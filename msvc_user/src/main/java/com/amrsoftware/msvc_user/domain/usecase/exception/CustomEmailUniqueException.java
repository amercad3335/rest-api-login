package com.amrsoftware.msvc_user.domain.usecase.exception;

public class CustomEmailUniqueException extends RuntimeException {
    public CustomEmailUniqueException(String message) {
        super(message);
    }
}
