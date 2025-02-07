package com.api.library.controllers;


import com.api.library.dtos.UsuarioDTO;
import com.api.library.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UsuarioController {
    // Serviço que lida com operações de usuario
    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    /**
     * Busca um Usuario pelo ID
     *
     * @return UsuarioDTO com os dados do Usuario encontrado
     */
    @Operation(summary = "Busca um Usuario pelo ID")
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findUsuarioById(id));
    }

    /**
     * Lista todos os usuarios
     *
     * @return Lista de UsuarioDTO com todos os usuarios cadastrados
     */
    @Operation(summary = "Lista todos os usuarios")
    @GetMapping("/find-all")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok().body(service.findUsuarioList());
    }

    /**
     * Cria um novo Usuario
     *
     * @return UsuarioDTO com os dados do Usuario recém-criado
     */
    @Operation(summary = "Cria um novo Usuario")
    @PostMapping("/create")
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(service.createUsuario(usuarioDTO));
    }

    /**
     * Atualiza um Usuario existente
     *
     * @return UsuarioDTO com os dados do Usuario atualizado
     */
    @Operation(summary = "Atualiza um Usuario existente")
    @PatchMapping("/patch")
    public ResponseEntity<UsuarioDTO> patch(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(service.patchUsuario(usuarioDTO));
    }

    /**
     * Deleta um Usuario pelo ID
     */
    @Operation(summary = "Deleta um Usuario pelo ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }
}
