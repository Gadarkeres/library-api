package com.api.library.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe DTO para Usuarios para retorno de dados e para envio de dados
 * Validação de campos obrigatórios
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDTO {
    private Integer id;
    @NotNull(message = "O nome é obrigatório")
    private String nome;
    @NotNull(message = "O email é obrigatório")
    @Email(message = "O email é inválido")
    private String email;
    @NotNull(message = "A data de cadastro é obrigatória")
    @PastOrPresent(message = "A data de cadastro não pode no futuro")
    private LocalDate dataCadastro;
    @NotNull(message = "O telefone é obrigatório")
    private String telefone;
}
