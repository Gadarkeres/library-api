package com.api.library.dtos;

public record EmprestimoDTO(
        EmprestimoCleanDTO emprestimo,
        UsuarioCleanDTO usuario,
        LivroCleanDTO livro
) {
}
