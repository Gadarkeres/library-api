package com.api.library.controllers;


import com.api.library.dtos.EmprestimoDTO;
import com.api.library.services.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<EmprestimoDTO> save(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {
        return ResponseEntity.ok().body(service.createEmprestimo(emprestimoDTO));
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<EmprestimoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findEmprestimoList());
    }


}
