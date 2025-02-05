package com.api.library.dtos;

import jakarta.validation.constraints.NotNull;

public record LivroCleanDTO (
    Integer id,
    @NotNull(message = "O título é obrigatório")
    String titulo,
    @NotNull(message = "O autor é obrigatório")
    String autor,
    @NotNull(message = "O ISBN é obrigatório")
    String isbn,
    @NotNull(message = "A categoria é obrigatória")
    String categoria
){};