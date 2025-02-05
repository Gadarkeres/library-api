package com.api.library.dtos;

import java.util.List;

public record UsuarioDTO(UsuarioCleanDTO usuario, List<EmprestimoCleanDTO> emprestimos) {
}
