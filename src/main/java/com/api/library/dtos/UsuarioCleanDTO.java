package com.api.library.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record UsuarioCleanDTO(
        Integer id,
        @NotNull(message = "O nome é obrigatório")
        String nome,
        @NotNull(message = "O email é obrigatório")
        String email,
        @NotNull(message = "A data de cadastro é obrigatória")
        @PastOrPresent(message = "A data de cadastro não pode no futuro")
        LocalDate dataCadastro,
        @NotNull(message = "O telefone é obrigatório")
        String telefone
) {
}
