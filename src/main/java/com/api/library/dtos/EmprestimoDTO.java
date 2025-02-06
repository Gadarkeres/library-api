package com.api.library.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmprestimoDTO {
    private Integer id;
    @NotNull(message = "A data de emprestimo é obrigatória")
    @PastOrPresent(message = "A data de emprestimo não pode no futuro")
    private LocalDate dataEmprestimo;
    @NotNull(message = "A data de devolucao é obrigatória")
    private LocalDate dataDevolucao;
    @NotNull(message = "O status é obrigatório")
    private String statusEmprestimo;

    private UsuarioDTO usuario;
    private LivroDTO livro;

}
