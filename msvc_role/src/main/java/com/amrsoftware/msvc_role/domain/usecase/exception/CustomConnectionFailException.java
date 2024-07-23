package com.amrsoftware.msvc_role.domain.usecase.exception;

public class CustomConnectionFailException extends RuntimeException {
    public CustomConnectionFailException(String message) {
        super(message);
    }
}
