package com.api.library.services;

import com.api.library.dtos.UsuarioDTO;
import com.api.library.entities.Usuario;
import com.api.library.exceptions.NotFoundException;
import com.api.library.repositories.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe responsável por fornecer serviços relacionados aos usuários
 * Inclui operações para listar, buscar, criar, atualizar e deletar usuários
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final ModelMapper mapper;

    /**
     * Retorna a lista de todos os usuários cadastrados
     *
     * @return Uma lista de UsuarioDTO contendo os dados de todos os usuários
     */
    @Transactional(rollbackFor = Exception.class)
    public List<UsuarioDTO> findUsuarioList() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream().map((usuario -> mapper.map(usuario, UsuarioDTO.class))).toList();
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @return Um UsuarioDTO contendo os dados do usuário encontrado
     * @throws NotFoundException Se o usuário não for encontrado
     */
    @Transactional(rollbackFor = Exception.class)
    public UsuarioDTO findUsuarioById(Integer id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        return mapper.map(usuario, UsuarioDTO.class);
    }

    /**
     * Cria um novo usuário no sistema
     *
     * @return Um UsuarioDTO com os dados do usuário recém-criado
     */
    @Transactional(rollbackFor = Exception.class)
    public UsuarioDTO createUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO.getDataCadastro().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de cadastro não pode ser no passado");
        }
        Usuario usuarioEntity = repository.save(mapper.map(usuarioDTO, Usuario.class));
        return mapper.map(usuarioEntity, UsuarioDTO.class);
    }


    /**
     * Atualiza  os dados de um usuário existente.
     *
     * @return Um UsuarioDTO com os dados do usuário atualizado
     */
    @Transactional(rollbackFor = Exception.class)
    public UsuarioDTO patchUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = repository.findById(usuarioDTO.getId()).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        BeanUtils.copyProperties(usuarioDTO, usuario);
        return mapper.map(repository.save(usuario), UsuarioDTO.class);
    }

    ;


    /**
     * Deleta um usuário pelo seu ID.
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUsuario(Integer id) {
        this.repository.deleteById(id);
    }


}
