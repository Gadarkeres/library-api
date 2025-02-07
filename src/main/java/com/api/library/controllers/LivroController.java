package com.api.library.controllers;


import com.api.library.dtos.LivroDTO;
import com.api.library.services.LivroService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@Data
public class LivroController {
    private final LivroService service;

    @Autowired
    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping("find-by-id/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findLivroById(id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<LivroDTO>> findAll() {
        return ResponseEntity.ok().body(service.findLivroList());
    }

    @PostMapping("/create")
    public ResponseEntity<LivroDTO> save(@RequestBody @Valid LivroDTO livroDTO) {
        return ResponseEntity.ok().body(service.createLivro(livroDTO));
    }

    @PatchMapping("/patch")
    public ResponseEntity<LivroDTO> patch(@RequestBody @Valid LivroDTO livroDTO) {
        return ResponseEntity.ok().body(service.patchLivro(livroDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteLivro(id);
        return ResponseEntity.ok().build();
    }

}
