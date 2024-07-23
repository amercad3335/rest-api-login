package com.amrsoftware.msvc_user.domain.usecase.exception;

public class CustomConnectionFailException extends RuntimeException {
    public CustomConnectionFailException(String message) {
        super(message);
    }
}
