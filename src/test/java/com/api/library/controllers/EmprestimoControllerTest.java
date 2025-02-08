package com.api.library.controllers;


import com.api.library.dtos.EmprestimoDTO;
import com.api.library.services.EmprestimoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmprestimoControllerTest {

    @InjectMocks
    private EmprestimoController controller;

    @Mock
    private EmprestimoService service;


    @Test
    @DisplayName("Deve lançar uma exceção caso a data de emprestimo seja maior que o dia atual")
    void deveLancarExcecaoCasoDataDeEmprestimoSejaMaiorQueDataAtual() {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(null, LocalDate.now().plusDays(1), null, null, null, null);
        when(service.createEmprestimo(emprestimoDTO)).thenThrow(new IllegalArgumentException("A data de emprestimo não pode ser no futuro"));
        assertThrows(IllegalArgumentException.class, () -> controller.save(emprestimoDTO));
    }

}
