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
    // Serviço que lida com operações de emprestimo
    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    /**
     * Cria um novo Emprestimo
     *
     * @return EmprestimoDTO com os dados do Emprestimo recém-criado
     */
    @PostMapping("/create")
    public ResponseEntity<EmprestimoDTO> save(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {
        return ResponseEntity.ok().body(service.createEmprestimo(emprestimoDTO));
    }

    /**
     * Lista todos os Emprestimos.
     *
     * @return Lista de EmprestimoDTO com todos os Emprestimos cadastrados.
     */
    @GetMapping("/find-all")
    public ResponseEntity<List<EmprestimoDTO>> findAll() {
        return ResponseEntity.ok().body(service.findEmprestimoList());
    }


}
