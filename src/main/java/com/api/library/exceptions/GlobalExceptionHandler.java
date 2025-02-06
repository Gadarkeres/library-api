package com.api.library.exceptions;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class) ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.badRequest().body("Ocorreu um erro: "+ e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<String> handleException(DataIntegrityViolationException e){
        if(e.getMessage().contains("email")){
            return ResponseEntity.badRequest().body("Esse email ja existe no sistema, por favor escolha outro.");
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro de integridade: "+ e.getMessage());
    }
}
