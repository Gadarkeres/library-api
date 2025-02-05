package com.api.library.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record  EmprestimoCleanDTO(
    Integer id,
    @NotNull(message = "A data de emprestimo é obrigatória")
    @PastOrPresent(message = "A data de emprestimo não pode no futuro")
    LocalDate dataEmprestimo,
    @NotNull(message = "A data de devolucao é obrigatória")
    LocalDate dataDevolucao,
    @NotNull(message = "O status é obrigatório")
    String statusEmprestimo

) {
}
