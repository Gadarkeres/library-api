package com.api.library.dtos;

import com.api.library.entities.Emprestimo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LivroDTO {
    private Integer id;
    @NotNull(message = "O título é obrigatório")
    private String titulo;
    @NotNull(message = "O autor é obrigatório")
    private String autor;
    @NotNull(message = "O ISBN é obrigatório")
    private String isbn;
    @NotNull(message = "A categoria é obrigatória")
    private String categoria;
}
