package com.amrsoftware.msvc_role.domain.usecase.exception;

public class CustomRoleUniqueException extends RuntimeException {
    public CustomRoleUniqueException(String message) {
        super(message);
    }
}
