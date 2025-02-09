package com.api.library.controllers;


import com.api.library.dtos.EmprestimoDTO;
import com.api.library.enums.Status;
import com.api.library.services.EmprestimoService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de testes para Emprestimo controller
 */
@ExtendWith(MockitoExtension.class)
class EmprestimoControllerTest {

    @InjectMocks
    private EmprestimoController controller;

    @Mock
    private EmprestimoService service;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Deve lançar uma exceção caso a data de emprestimo seja maior que o dia atual")
    void deveLancarExcecaoCasoDataDeEmprestimoSejaMaiorQueDataAtual() {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(null, LocalDate.now().plusDays(3), LocalDate.now().plusDays(7), Status.EMPRESTADO, null, null);
        Set<ConstraintViolation<EmprestimoDTO>> violations = validator.validate(emprestimoDTO);
        boolean result = violations.stream().anyMatch((v) -> v.getMessage().equals("A data de empréstimo não pode ser no futuro"));

        assertTrue(result);
    }

}
