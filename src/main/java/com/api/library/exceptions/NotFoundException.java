package com.api.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada para recursos não encontrados
 * Essa exceção é lançada quando um recurso solicitado não é encontrado no banco de dados
 * Ao ser lançada, retorna automaticamente um status HTTP 404 (Not Found)
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(){}

    /**
     * Construtor que permite definir uma mensagem personalizada para a exceção
     *
     */
    public NotFoundException(String message) {
        super(message);
    }
}
