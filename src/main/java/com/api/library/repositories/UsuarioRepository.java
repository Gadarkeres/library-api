package com.api.library.repositories;

import com.api.library.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Usuario
 * Permite realizar operações de persistência para empréstimos, como busca, criação e remoção
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
