package com.api.library.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por lidar com exceções globais na API.
 * Captura e trata erros de validação, exceções inesperadas e violações de integridade de dados,
 * retornando respostas padronizadas e informativas para o cliente
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Trata exceções de validação de argumentos inválidos em requisições
     * Retorna um mapa com os campos inválidos e suas respectivas mensagens de erro
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Trata exceções genéricas que não possuem um manipulador específico.
     * Retorna uma mensagem de erro personalizada para evitar a exposição de detalhes técnicos ao cliente.
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body("Ocorreu um erro: " + e.getMessage());
    }

    /**
     * Trata exceções de violação de integridade de dados, como tentativas de inserir registros duplicados.
     * Se o erro estiver relacionado ao email, retorna uma mensagem específica.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> handleException(DataIntegrityViolationException e) {
        if (e.getMessage().contains("email")) {
            return ResponseEntity.badRequest().body("Esse email ja existe no sistema, por favor escolha outro.");
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro de integridade: " + e.getMessage());
    }
}
