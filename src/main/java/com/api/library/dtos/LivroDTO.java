package com.api.library.dtos;

import com.api.library.enums.Categoria;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Categoria categoria;
}
