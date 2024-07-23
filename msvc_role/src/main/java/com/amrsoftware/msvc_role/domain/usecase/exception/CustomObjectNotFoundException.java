package com.amrsoftware.msvc_role.domain.usecase.exception;

public class CustomObjectNotFoundException extends RuntimeException {
    public CustomObjectNotFoundException(String message) {
        super(message);
    }
}
