package com.api.library.controllers;

import com.api.library.dtos.UsuarioDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de testes para Usuario controller
 */
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve lançar exceção caso a data de cadastro seja maior que a data atual")
    void deveLancarExcecaoCasoDataDeCadastroSejaMaiorQueDataAtual() {
        UsuarioDTO dto = new UsuarioDTO(null, "Nome", "email@example.com", LocalDate.now().plusDays(3), "99999999");

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(dto);
        boolean result = violations.stream().anyMatch((v) -> v.getMessage().equals("A data de cadastro não pode ser no futuro"));

        assertTrue(result);

    }

    @Test
    @DisplayName("Deve lançar exceção caso o email seja inválido")
    void deveLancarExcecaoCasoEmailSejaInvalido() {
        UsuarioDTO dto = new UsuarioDTO(null, "Nome", "email", LocalDate.now(), "99999999");

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(dto);
        boolean result = violations.stream().anyMatch((v) -> v.getMessage().equals("O email é inválido"));

        assertTrue(result);
    }
}