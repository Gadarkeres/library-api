package com.api.library.dtos;

import com.api.library.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe DTO para Emprestimo para retorno de dados e para envio de dados
 * Validação de campos obrigatórios
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmprestimoDTO {
    private Integer id;
    @NotNull(message = "A data de empréstimo é obrigatória")
    @PastOrPresent(message = "A data de empréstimo não pode ser no futuro")
    private LocalDate dataEmprestimo;
    @NotNull(message = "A data de devolucao é obrigatória")
    private LocalDate dataDevolucao;
    @NotNull(message = "O status é obrigatório")
    private Status statusEmprestimo;

    private Integer usuarioId;
    private Integer livroId;

}
