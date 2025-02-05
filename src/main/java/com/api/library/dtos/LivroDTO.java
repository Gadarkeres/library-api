package com.api.library.dtos;

import com.api.library.entities.Emprestimo;

import java.util.List;

public record LivroDTO (LivroCleanDTO livroCleanDTO, List<Emprestimo> emprestimos) {
}
