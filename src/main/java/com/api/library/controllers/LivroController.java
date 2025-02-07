package com.api.library.controllers;


import com.api.library.dtos.LivroDTO;
import com.api.library.services.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Operações relacionadas aos livros")
public class LivroController {
    // Serviço que lida com as operações de livros
    private final LivroService service;

    @Autowired
    public LivroController(LivroService service) {
        this.service = service;
    }

    /**
     * Busca um livro pelo ID
     *
     * @return LivroDTO com os dados do livro encontrado
     */
    @Operation(summary = "Busca um livro pelo ID")
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findLivroById(id));
    }

    /**
     * Lista todos os livros
     *
     * @return Lista de LivroDTO com todos os livros cadastrados
     */
    @Operation(summary = "Lista todos os livros")
    @GetMapping("/find-all")
    public ResponseEntity<List<LivroDTO>> findAll() {
        return ResponseEntity.ok().body(service.findLivroList());
    }

    /**
     * Cria um novo livro.
     *
     * @return LivroDTO com os dados do livro recém-criado
     */
    @Operation(summary = "Cria um novo livro")
    @PostMapping("/create")
    public ResponseEntity<LivroDTO> save(@RequestBody @Valid LivroDTO livroDTO) {
        return ResponseEntity.ok().body(service.createLivro(livroDTO));
    }

    /**
     * Atualiza parcialmente um livro existente
     *
     * @return LivroDTO com os dados do livro atualizado
     */
    @Operation(summary = "Atualiza um livro existente")
    @PatchMapping("/patch")
    public ResponseEntity<LivroDTO> patch(@RequestBody @Valid LivroDTO livroDTO) {
        return ResponseEntity.ok().body(service.patchLivro(livroDTO));
    }

    /**
     * Deleta um livro pelo ID
     */
    @Operation(summary = "Deleta um livro pelo ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteLivro(id);
        return ResponseEntity.ok().build();
    }

}
