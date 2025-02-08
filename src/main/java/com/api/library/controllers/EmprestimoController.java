package com.api.library.controllers;


import com.api.library.dtos.EmprestimoDTO;
import com.api.library.dtos.LivroDTO;
import com.api.library.dtos.PatchEmprestimoDTO;
import com.api.library.services.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@Tag(name = "Emprestimos", description = "Operações relacionadas aos empréstimos")
@RequiredArgsConstructor
public class EmprestimoController {
    private final EmprestimoService service;

    /**
     * Cria um novo Emprestimo
     *
     * @return EmprestimoDTO com os dados do Emprestimo recém-criado
     */
    @Operation(summary = "Cria um novo Emprestimo")
    @PostMapping("/create")
    public ResponseEntity<EmprestimoDTO> save(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {
        return ResponseEntity.ok().body(service.createEmprestimo(emprestimoDTO));
    }

    /**
     * Lista todos os Emprestimos.
     *
     * @return Lista de EmprestimoDTO com todos os Emprestimos cadastrados.
     */
    @Operation(summary = "Lista todos os Emprestimos")
    @GetMapping("/find-all")
    public ResponseEntity<List<EmprestimoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findEmprestimoList());
    }

    /**
     *  Atualiza parcialmente um emprestimo existente
     * @param patchEmprestimoDTO
     * @return EmprestimoDTO com os dados do emprestimo atualizado
     */
    @Operation(summary = "atualizar um emprestimo existente")
    @PatchMapping("/patch")
    public ResponseEntity<EmprestimoDTO> patch(@RequestBody @Valid PatchEmprestimoDTO patchEmprestimoDTO) {
        return ResponseEntity.ok().body(service.patchEmprestimo(patchEmprestimoDTO));
    }

    /**
     * Verifica se um livro pode ser emprestado
     * @param usuarioId
     * @return Lista de LivroDTO com os livros recomendados
     */
    @Operation(summary = "recomendar livros para o usuario")
    @GetMapping("recomendar-livros/{usuarioId}")
    public ResponseEntity<List<LivroDTO>> recomendarLivros(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok().body(service.recomendarLivros(usuarioId));
    }

}
