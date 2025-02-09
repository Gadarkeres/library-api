package com.api.library.controllers;

import com.api.library.dtos.UsuarioDTO;
import com.api.library.services.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Classe de testes para Usuario controller
 */
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    @Mock
    private UsuarioService service;

    @Test
    @DisplayName("Deve lançar exceção caso a data de cadastro seja maior que a data atual")
    void deveLancarExcecaoCasoDataDeCadastroSejaMaiorQueDataAtual() {
        UsuarioDTO dto = new UsuarioDTO(null, "name", "email@example.com", LocalDate.now().plusDays(3), "99999999");

        when(service.createUsuario(dto)).thenThrow(new IllegalArgumentException("A data de cadastro não pode ser no futuro"));
        assertThrows(IllegalArgumentException.class, () -> controller.save(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção caso o email seja inválido")
    void deveLancarExcecaoCasoEmailSejaInvalido() {
        UsuarioDTO dto = new UsuarioDTO(null, "name", "email", LocalDate.now(), "99999999");

        when(service.createUsuario(dto)).thenThrow(new IllegalArgumentException("O email é inválido"));
        assertThrows(IllegalArgumentException.class, () -> controller.save(dto));
    }
}