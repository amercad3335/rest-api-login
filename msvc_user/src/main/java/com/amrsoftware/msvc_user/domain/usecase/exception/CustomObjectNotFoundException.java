package com.amrsoftware.msvc_user.domain.usecase.exception;

public class CustomObjectNotFoundException extends RuntimeException {
    public CustomObjectNotFoundException(String message) {
        super(message);
    }
}
