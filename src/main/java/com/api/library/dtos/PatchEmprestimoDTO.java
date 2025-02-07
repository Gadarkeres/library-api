package com.api.library.dtos;

import com.api.library.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe DTO para Emprestimo para retorno de dados e para envio de dados
 * Validação de campos obrigatórios
 *     */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatchEmprestimoDTO {
    @NotNull(message = "O id é do emprestimo para atualização é obrigatório")
    private Integer id;
    private LocalDate dataDevolucao;
    private Status statusEmprestimo;
}
