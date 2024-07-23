package com.amrsoftware.msvc_role.infrastructure.controller.exception;

import com.amrsoftware.msvc_role.domain.model.error.Error;
import com.amrsoftware.msvc_role.domain.usecase.exception.CustomConnectionFailException;
import com.amrsoftware.msvc_role.domain.usecase.exception.CustomObjectNotFoundException;
import com.amrsoftware.msvc_role.domain.usecase.exception.CustomRoleUniqueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomGlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validFields(MethodArgumentNotValidException ex) {
        var error = new HashMap<String, String>();

        ex.getBindingResult().getFieldErrors().forEach(err -> {
            error.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CustomObjectNotFoundException.class)
    public ResponseEntity<Error> objectNotFound(Exception ex) {
        var error = Error.builder()
            .error(ex.getMessage())
            .message("Error, el registro buscado no esta registrado en el sistema")
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timestamp(LocalDateTime.now())
            .build();

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(CustomRoleUniqueException.class)
    public ResponseEntity<Error> roleValidUnique(Exception ex) {
        var error = Error.builder()
            .error(ex.getMessage())
            .message("Error, la description del rol esta duplicada en el sistema")
            .status(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .build();

        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(CustomConnectionFailException.class)
    public ResponseEntity<Error> errorConnection(Exception ex) {
        var error = Error.builder()
                .error(ex.getMessage())
                .message("Error, no se pudo establecer la conexion")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.internalServerError().body(error);
    }


}
