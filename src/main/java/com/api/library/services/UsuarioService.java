package com.api.library.services;

import com.api.library.dtos.UsuarioDTO;
import com.api.library.entities.Usuario;
import com.api.library.exceptions.NotFoundException;
import com.api.library.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public UsuarioService(UsuarioRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UsuarioDTO> findUsuarioList() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream().map((usuario -> mapper.map(usuario, UsuarioDTO.class))).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public UsuarioDTO findUsuarioById(Integer id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return mapper.map(usuario, UsuarioDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public UsuarioDTO saveDTO(@Valid UsuarioDTO usuarioDTO) {
        Usuario usuarioEntity = repository.save(mapper.map(usuarioDTO, Usuario.class));
        return mapper.map(usuarioEntity, UsuarioDTO.class);
    }


}
