package com.api.library.controllers;


import com.api.library.dtos.UsuarioDTO;
import com.api.library.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Validated
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
    @GetMapping("find-by-id/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findUsuarioById(id));
    }

    /**
     * Lista todos os usuarios
     *
     * @return Lista de UsuarioDTO com todos os usuarios cadastrados
     */
    @GetMapping("/find-all")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        return ResponseEntity.ok().body(service.findUsuarioList());
    }

    /**
     * Cria um novo Usuario
     *
     * @return UsuarioDTO com os dados do Usuario recém-criado
     */
    @PostMapping("/create")
    public ResponseEntity<UsuarioDTO> save(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(service.createUsuario(usuarioDTO));
    }

    /**
     * Atualiza um Usuario existente
     *
     * @return UsuarioDTO com os dados do Usuario atualizado
     */
    @PatchMapping("/patch")
    public ResponseEntity<UsuarioDTO> patch(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok().body(service.patchUsuario(usuarioDTO));
    }

    /**
     * Deleta um Usuario pelo ID
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }
}
